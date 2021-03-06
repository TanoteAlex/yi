import {Component} from '@angular/core';
import {NativeProvider} from "../../services/native-service/native";
import {OrderProvider} from "../../services/order-service/order";
import {AlertController, Events, NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {Order} from "../../domain/original/order.model";
import {MemberProvider} from "../../services/member-service/member";
import {REFRESH_SHOPPINGCART, SUCCESS} from "../Constants";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {CartService} from "../../services/member-service/cart.service";
import {AppConfig} from "../app.config";

@Component({
    selector: 'app-order-detail',
    templateUrl: './order-detail.page.html',
    styleUrls: ['./order-detail.page.scss'],
})
export class OrderDetailPage {

    order: Order;

    /*头部banners图片样式名*/
    banner;

    /*物流信息*/
    logistics;

    // state：
    //      0：在途，即货物处于运输过程中；
    //      1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
    //      2：疑难，货物寄送过程出了问题；
    //      3：签收，收件人已签收；
    //      4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
    //      5：派件，即快递正在进行同城派件；
    //      6：退回，货物正处于退回发件人的途中；
    orderFollowMsgList = ["您的订单已出库，正在运输中",
        "您的订单已出库，正在揽件中",
        "您的订单在运输途中出现了问题，请联系物流公司",
        "您的订单已签收",
        "您的订单已被拒收",
        "您的订单正在派件中，请留意",
        "您的订单正在退货途中"];

    constructor(public nativeProvider: NativeProvider, public orderProvider: OrderProvider, public navCtrl: NavController, public route: ActivatedRoute,
                public alertCtrl: AlertController, public commodityProvider: CommodityProvider, public events: Events, public weChatProvider: WechatService,
                public cartProvider: CartService, public appConfig: AppConfig) {
    }

    ionViewWillEnter() {
        this.route.data.subscribe(e => {
            this.order = e.data;
            this.transform(this.order);
            this.getLogistics();
        });
        /*this.orderProvider.getOrder(this.route.params["value"].orderId).then(e => {
            if (e.result == "SUCCESS") {
                this.order = e.data;
                this.transform(this.order);
                this.getLogistics();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        })*/
    }

    getLogistics() {
        if (this.order.expressCompany && this.order.expressNo) {
            this.orderProvider.getExpressDelivery(this.order.expressCompany, this.order.expressNo).then(e => {
                // this.orderProvider.getExpressDelivery('yuantong'), '801597894651430271').then(e => {
                let data: any = e;
                this.logistics = JSON.parse(data);

                if (this.logistics.state && this.logistics.data[0]) {
                    this.order.orderFollowMsg = this.orderFollowMsgList[this.logistics.state];
                    this.order.orderFollowMsgTime = this.logistics.data[0].time;
                }
            })
        } else {
            this.order.orderFollowMsg = "您的订单已进入库房，准备出库";
            this.order.orderFollowMsgTime = this.order.paymentTime;
        }
    }


    /*一下均为订单详情各种处理*/
    cancelOrder(order) {
        this.orderProvider.cancelOrder(order.id).then(e => {
            if (e.result == SUCCESS) {
                this.nativeProvider.showToastFormI4("取消订单成功", () => {
                    this.navCtrl.goBack();
                });
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    goOrderPay(order) {
        if (this.appConfig.isApp) {
            let orderType;
            if (order.orderType) {
                if (order.nationalGroupRecordId) {
                    orderType = 'joinGroup';
                } else {
                    orderType = 'openGroup';
                }
            }
            this.navCtrl.navigateForward(["PaymentOrderPage", {ids: [order.id], orderType: orderType}]);
        }else{
            let weChatVo = {
                memberId: MemberProvider.getLoginMember().id,
                openId: MemberProvider.getLoginMember().spOpenId,
                orderIds: [order.id],
                totalFee: order.payAmount
            };

            //订单类型，普通订单undefined, openGroup,joinGroup
            let orderType;
            if (order.orderType) {
                if (order.nationalGroupRecordId) {
                    orderType = 'joinGroup';
                } else {
                    orderType = 'openGroup';
                }
            }

            this.weChatProvider.readyPay(weChatVo).then(e => {
                if (e.result == "SUCCESS") {
                    this.weChatProvider.pay(e.data, () => {
                        this.navCtrl.navigateForward(["PaymentFinishPage", {totalAmount: order.payAmount, orderType: orderType}])
                    }, () => {
                        return
                    }, () => {
                        return
                    })
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                }
            }, err => this.nativeProvider.showToastFormI4(err.message));
        }
    }

    goOrderFollow(order) {
        this.navCtrl.navigateForward(["OrderFollowPage", {orderId: order.id}]);
    }

    async confirmReceive(order) {
        const alert = await this.alertCtrl.create({
            header: '确认收货？',
            buttons: [{
                text: '确认',
                handler: () => {
                    this.orderProvider.confirmReceive(order.id).then(e => {
                        if (e.result == SUCCESS) {
                            this.nativeProvider.showToastFormI4("已确认收货", () => {
                                if (e.data && e.data.length) { //返回信息，是否有分批发放的代金券信息，有就显示响应的提示
                                    e.data.forEach(e1 => this.presentAlert(e1))
                                } else {
                                    this.ionViewWillEnter();
                                }
                            });
                        } else {
                            this.nativeProvider.showToastFormI4(e.message);
                        }
                    }, err => {
                        this.nativeProvider.showToastFormI4(err.message);
                    })
                }
            }, {
                text: '取消',
                role: 'cancel',
            }]
        });
        await alert.present();
    }

    //提示相应的分步发放的代金券
    async presentAlert(data) {
        const alert = await this.alertCtrl.create({
            message: "获得" + data + "元代金券",
            buttons: ['确认']
        });
        alert.onWillDismiss().then(() => this.ionViewWillEnter());
        await alert.present();
    }

    async delOrder(order) {
        const alert = await this.alertCtrl.create({
            header: '确认删除订单？',
            buttons: [{
                text: '确认',
                handler: () => {
                    this.orderProvider.delOrder(order.id).then(e => {
                        if (e.result == SUCCESS) {
                            this.nativeProvider.showToastFormI4("删除成功", () => {
                                this.navCtrl.goBack();
                            });
                        } else {
                            this.nativeProvider.showToastFormI4(e.message);
                        }
                    }, err => {
                        this.nativeProvider.showToastFormI4(err.message);
                    })
                }
            }, {
                text: '取消',
                role: 'cancel',
            }]
        });
        await alert.present();
    }

    goApplyReturn(order) {
        this.navCtrl.navigateForward(["ApplyReturnPage", {orderId: order.id}]);
    }

    goEvaluation(order) {
        this.navCtrl.navigateForward(["EvaluationPage", {orderId: order.id}])
    }

    shippngAgain(order) {
        for (let i = 0; i < order.saleOrderItems.length; i++) {
            this.cartProvider.addShopCart(MemberProvider.getLoginMember().id, order.saleOrderItems[i].product.id, order.saleOrderItems[i].quantity).then(e => {
                if (e.result == "SUCCESS") {
                    if (i + 1 == order.saleOrderItems.length) {
                        this.nativeProvider.showToastFormI4("已添加到购物车!", () => {
                            this.events.publish(REFRESH_SHOPPINGCART);
                            this.navCtrl.navigateForward("ShoppingCartPage")
                        });
                    }
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
            })
        }
    }

    goCheckEvaluation(order) {
        this.navCtrl.navigateForward(["CommodityPage", {
            segment: "evaluation",
            id: order.saleOrderItems[0].commodityId //todo
        }]);
    }

    goRecordDetailsPage(order) {
        this.navCtrl.navigateForward(["RecordDetailPage", {orderId: order.id}])
    }

    /*字段转换*/
    private transform(data) {
        data.orderStateMsg = ["", "等待支付", "等待发货", "等待收货", "交易完成", "交易关闭"][data.orderState];
        if (data.orderState == 4 && data.commentState == 1 && data.afterSaleState == 1) {
            data.orderStateMsg = "待评价";
        }
        if (data.afterSaleState == 2) {
            data.orderStateMsg = "申请售后中";
        }
        data.payMode = ["", "支付宝", "微信", "银联", "余额"][data.payMode];

        // data.commoditiesAmount = 0;
        // data.saleOrderItems.forEach(e1 => {
        //     data.commoditiesAmount += e1.total;
        // });
        if (data.consigneeAddr) {
            data.consigneeAddr = this.nativeProvider.transferAddress(data.consigneeAddr);
        }
        data.productList = this.nativeProvider.productListChange(data.saleOrderItems);

        this.banner = ["", "unpaid", "deliver", "deliver", "finish", "cancel"][data.orderState];
    }
}
