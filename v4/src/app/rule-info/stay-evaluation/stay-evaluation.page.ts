import {Component, OnInit} from '@angular/core';

import {NavController} from "@ionic/angular";
import {NativeProvider} from "../../../services/native-service/native";
import {PageQuery} from "../../../util/page-query.model";
import {Order} from "../../../domain/original/order.model";
import {MemberProvider} from "../../../services/member-service/member";
import {OrderProvider} from "../../../services/order-service/order";

@Component({
    selector: 'app-stay-evaluation',
    templateUrl: './stay-evaluation.page.html',
    styleUrls: ['./stay-evaluation.page.scss'],
})
export class StayEvaluationPage implements OnInit {

    ngOnInit() {
    }

    evaluation = "stay";
    pageQuery: PageQuery = new PageQuery();

    constructor(public nativeProvider: NativeProvider, public orderProvider: OrderProvider, public navCtrl: NavController) {

    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.listFilter(this.evaluation);
    }

    list: Order[] = [];

    listFilter(type) {
        this.pageQuery.resetRequests();
        this.queryFilter(type);
        this.getData(this.pageQuery);
    }

    /*分割导航，不同分页请求*/
    queryFilter(type) {
        switch (type) {
            case "stay":
                this.pageQuery.addOrFilter('state', 4, 'eq');
                this.pageQuery.addOrFilter('state', 10, 'eq');
                break;
            case "already":
                this.pageQuery.addOrFilter('state', 11, 'eq');
                break;
            default:
                break;
        }
    }

    private getData(page: PageQuery) {
        this.orderProvider.queryOrder(page.requests).then(data => {
            this.list = data.content;
            this.pageQuery.covertResponses(data);
            this.transform(this.list);
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    /*字段转换*/
    transform(data) {
        for (let i = 0; i < data.length; i++) {
            data[i].orderStateMsg = ["", "等待支付", "等待发货", "等待收货", "交易完成", "交易关闭", "", "", "", "", "待评价", "已评价"][data[i].state];
            if (data[i].saleOrderItems) {
                /*data[i].productList = data[i].saleOrderItems.map(e => {
                    return {
                        id: e.product.commodity.id,
                        imgPath: e.product.commodity.imgPath,
                        productName: e.product.commodity.commodityName,
                        productShortName: e.commodityShortName,
                        discount: "",
                        price: e.price,
                        quantity: e.quantity
                    }
                });*/
            }
        }
    }

    doRefresh(refresher) {
        this.orderProvider.queryOrder(this.pageQuery.requests).then(data => {
            this.pageQuery.covertResponses(data);
            this.list = data.content;
            this.transform(this.list);
            refresher.target.complete();
        }, err => refresher.target.complete())
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.orderProvider.queryOrder(this.pageQuery.requests).then(data => {
                    this.list = this.list.concat(data.content);
                    this.transform(this.list);
                    this.pageQuery.covertResponses(data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

    goEvaluation(product) {
        this.navCtrl.navigateForward(["EvaluationPage", {products: [{productId: product.id, productImg: product.imgPath, productName: product.productName}]}]);
    }

    goCheckEvaluation(product) {
        this.navCtrl.navigateForward(["CommodityPage", {segment: "evaluation", id: product.id}]);
    }


}
