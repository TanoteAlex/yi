import {Component} from '@angular/core';
import {LOGISTICSCOMPANY} from "../Constants";
import {Order} from "../../domain/original/order.model";
import {NativeProvider} from "../../services/native-service/native";
import {OrderProvider} from "../../services/order-service/order";
import {NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-order-follow',
    templateUrl: './order-follow.page.html',
    styleUrls: ['./order-follow.page.scss'],
})
export class OrderFollowPage {

    order: Order;

    img;

    logistics;

    totalItemNum: number = 0;

    logisticsName: string;
    /**
     * 回传的state状态，1已发货，0运输中，5派件中，3已签收，转为stepImgNum
     * @type {number} 1已发货，2运输中，3派件中，4已签收
     */
    stepImgNum: number = 0;
    progress = [{tip: "已发货",}, {tip: "运输中",}, {tip: "派件中",}, {tip: "已签收",}];

    constructor(public nativeProvider: NativeProvider, public orderProvider: OrderProvider, public navCtrl: NavController, public route: ActivatedRoute) {
    }

    ionViewWillEnter() {
        this.orderProvider.getOrder(this.route.params["value"].orderId).then(e => {
            if (e.result == "SUCCESS") {
                this.order = e.data;
                this.img = e.data.saleOrderItems[0].product.productImgPath;
                this.order.saleOrderItems.forEach(e => {
                    this.totalItemNum += e.quantity
                });
                this.getLogistics();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    getLogistics() {
        if (this.order.expressCompany && this.order.expressNo) {
            this.orderProvider.getExpressDelivery(this.order.expressCompany, this.order.expressNo).then(e => {
                // this.orderProvider.getExpressDelivery('baishiwuliu', ' 70250300836935 ').then(e => {
                let data: any = e;
                this.logistics = JSON.parse(data);
                this.stepImgNum = [2, 1, 0, 4, 0, 3, 0][parseInt(this.logistics.state)];
                //为百事快递状态处理所定制
                if (this.order.expressCompany == 'baishiwuliu'){
                    let e = this.logistics.data;
                    for(let i=0;i<this.logistics.data.length;i++){
                        if(e[i].context.indexOf('已签收') != -1) {
                            this.stepImgNum = 4;
                            break;
                        }else if(e[i].context.indexOf('正在派件') != -1) {
                            this.stepImgNum = 3;
                            break;
                        }else if (e[i].context.indexOf('已揽收') != -1) {
                            this.stepImgNum = 1;
                            break;
                        } else {
                            this.stepImgNum = 2;
                        }
                    }
                }

                this.logisticsName = companyTransform(this.order.expressCompany);
            })
        } else {
            this.logisticsName = "暂无信息";
            this.order.expressNo = "暂无信息";
        }

        function companyTransform(name) {
            return LOGISTICSCOMPANY[name];
        }
    }



}
