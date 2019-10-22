import {Component, OnInit} from '@angular/core';

import {NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";
import {PageQuery} from "../../util/page-query.model";
import {Order} from "../../domain/original/order.model";
import {OrderProvider} from "../../services/order-service/order";
import {MemberProvider} from "../../services/member-service/member";
import {AFTER_SALE_MSG} from "../Constants";

@Component({
    selector: 'app-after-sales',
    templateUrl: './after-sales.page.html',
    styleUrls: ['./after-sales.page.scss'],
})
export class AfterSalesPage {

    afterSale: string = "apply";
    pageQuery: PageQuery = new PageQuery();

    list: Order[] = [];
    isLoading: boolean = false;

    constructor(public nativeProvider: NativeProvider, public orderProvider: OrderProvider, public navCtrl: NavController) {
    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.ordersFilter(this.afterSale);
    }

    ordersFilter(type) {
        this.queryFilter(type);
        this.getData(this.pageQuery);
    }

    queryFilter(type) {
        this.pageQuery.resetRequests()
        switch (type) {
            case "apply":
                this.pageQuery.addOrFilter('afterSaleState', 1, 'eq');
                this.pageQuery.addOrFilter('afterSaleState', 4, 'eq');
                break;
            case "applyRecord":
                this.pageQuery.addOrFilter('afterSaleState', 2, 'eq');
                this.pageQuery.addOrFilter('afterSaleState', 3, 'eq');
                break;
            default:
                break;
        }
    }

    transform(data) {
        for (let i = 0; i < data.length; i++) {
            data[i].afterSaleMsg = AFTER_SALE_MSG[data[i].afterSaleState];
            if (!data[i].afterSaleMsg){
                data[i].afterSaleMsg = "实付金额为："+ data[i].payAmount;
            }
            data[i].productList = this.nativeProvider.productListChange(data[i].saleOrderItems);
        }
    }

    private getData(page: PageQuery) {
        this.list = [];
        this.isLoading = true;
        this.orderProvider.queryOrder(page.requests).then(data => {
            this.list = data.content;
            this.pageQuery.covertResponses(data);
            this.transform(this.list);
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    doRefresh(refresher) {
        this.queryFilter(this.afterSale);
        this.orderProvider.queryOrder(this.pageQuery.requests).then(data => {
            this.list = data.content;
            this.pageQuery.covertResponses(data);
            this.transform(this.list);
            refresher.target.complete();
        }, err => refresher.target.complete())
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.orderProvider.queryOrder(this.pageQuery.requests).then(data => {
                    this.list.push(...data.content)
                    this.pageQuery.covertResponses(data);

                    // this.list = this.list.concat(data.content);
                    this.transform(this.list);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

    goApplyReturn(order) {
        this.navCtrl.navigateForward(["ApplyReturnPage", {orderId: order.id}]);
    }

    goAuditProgress() {
        this.navCtrl.navigateForward(["AuditProgressPage"]);
    }

    goRecordDetailsPage(order) {
        this.navCtrl.navigateForward(["RecordDetailPage", {orderId: order.id}])
    }


}
