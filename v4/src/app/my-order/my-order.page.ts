import {Component, OnInit} from '@angular/core';
import {Order} from "../../domain/original/order.model";
import {PageQuery} from "../../util/page-query.model";
import {AlertController, Events, ModalController, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {OrderProvider} from "../../services/order-service/order";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {ActivatedRoute} from "@angular/router";
import {NativeProvider} from "../../services/native-service/native";
import {REFRESH_CUSTOMERCENTER, REFRESH_SHOPPINGCART, SUCCESS} from "../Constants";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {customOnError, customOnResult} from "../../services/http-service/http-service";
import {CartService} from "../../services/member-service/cart.service";
import {RewardAlertModalPage} from "../rule-info/reward-alert-modal/reward-alert-modal.page";
import {AppConfig} from "../app.config";

@Component({
    selector: 'app-my-order',
    templateUrl: './my-order.page.html',
    styleUrls: ['./my-order.page.scss'],
})
export class MyOrderPage implements OnInit {
    state = "all";

    list: Order[] = [];

    pageQuery: PageQuery = new PageQuery();

    isLoading: boolean = false;

    /**
     * 点击返回是，返回的页面
     * 这里正常为undefined，一般传入customerCenter，
     */
    goBackPage: string;

    isApp:boolean=false;

    constructor(public nativeProvider: NativeProvider, public route: ActivatedRoute, public alertCtrl: AlertController, public memberProvider: MemberProvider,
                public orderProvider: OrderProvider, public navCtrl: NavController, public commodityProvider: CommodityProvider, public events: Events,
                public weChatProvider: WechatService, public cartProvider: CartService, public modalCtrl: ModalController,public appConfig: AppConfig) {
        /*unpaid或deliver入口进入*/
        if (this.route.params["value"].state != 'undefined') {
            this.state = this.route.params["value"].state;
        }
        this.goBackPage = this.route.params["value"].goBackPage;

        this.isApp = this.appConfig.isApp;
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.isLoading = true;
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.listFilter1(this.state);
    }

    ionViewWillLeave() {
        this.events.publish(REFRESH_CUSTOMERCENTER);
    }

    listFilter(type) {       //0,1整合在一起，下拉刷新会有一点小问题，稍微优化了一下
        this.list = [];
        this.isLoading = true;
        this.listFilter1(type)
    }

    listFilter1(type) {
        this.pageQuery.resetRequests();
        this.queryFilter(type);
        this.getData(this.pageQuery);
    }

    /*分割导航，不同分页请求*/
    queryFilter(type) {
        switch (type) {
            case "unpaid":
                this.pageQuery.addOrFilter('orderState', 1, 'eq');
                break;
            case "deliver":
                this.pageQuery.addOrFilter('orderState', 2, 'eq');
                this.pageQuery.addOrFilter('orderState', 3, 'eq');
                break;
            case "stayEvaluation":
                this.pageQuery.addOrFilter('commentState', 1, 'eq');
                break;
            case "canceled":
                this.pageQuery.addOrFilter('orderState', 5, 'eq');
                break;
            default:
                break;
        }
        this.pageQuery.addSort('createTime', 'desc');
    }

    private getData(page: PageQuery) {
        this.orderProvider.queryOrder(page.requests).then(data => {
            this.pageQuery.covertResponses(data);
            this.transform(data.content);
            if (this.list == data.content) {
                return
            } else {
                this.list = data.content;
            }
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    goOrderDetail(order) {
        this.navCtrl.navigateForward(["OrderDetailPage", {orderId: order.id}], false);
    }

    cancelOrder(order) {
        this.orderProvider.cancelOrder(order.id).then(e => {
            if (e.result == SUCCESS) {
                this.nativeProvider.showToastFormI4("取消订单成功", () => {
                    this.ionViewWillEnter();
                });
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
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
        }else {
            let weChatVo = {
                memberId: MemberProvider.getLoginMember().id,
                orderIds: [order.id],
                openId: MemberProvider.getLoginMember().spOpenId,
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
                        customOnResult(e, () => {
                            this.nativeProvider.showToastFormI4("已确认收货", () => {
                                this.state = 'stayEvaluation';
                                if (e.data && e.data.length) { //返回信息，是否有分批发放的代金券信息，有就显示响应的提示
                                    this.presentAlert(e.data)
                                } else {
                                    this.ionViewWillEnter();
                                }
                            });
                        })
                    }, err => customOnError(err))
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
        const modal = await this.modalCtrl.create({
                component: RewardAlertModalPage,
                componentProps: {data: data}
            }
        );
        modal.onWillDismiss().then(() => this.ionViewWillEnter());
        await modal.present();
    }

    goEvaluation(order) {
        this.navCtrl.navigateForward(["EvaluationPage", {orderId: order.id}])
    }

    goCheckEvaluation(order) {
        this.navCtrl.navigateForward(["CommodityPage", {
            id: order.saleOrderItems[0].commodityId,
            segment: "evaluation"
        }]);
    }

    shippngAgain(order) {
        for (let i = 0; i < order.saleOrderItems.length; i++) {
            this.cartProvider.addShopCart(MemberProvider.getLoginMember().id, order.saleOrderItems[i].product.id, order.saleOrderItems[i].quantity).then(e => {
                if (e.result == "SUCCESS") {
                    if (i + 1 == order.saleOrderItems.length) {
                        this.events.publish(REFRESH_SHOPPINGCART);
                        this.navCtrl.navigateForward("/app/tabs/shoppingCart")
                    }
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
            })
        }
    }

    async delOrder(order) {
        const alert = await this.alertCtrl.create({
            header: '确认删除订单？',
            buttons: [{
                text: '确认',
                handler: () => {
                    this.orderProvider.delOrder(order.id).then(e => {
                        if (e.result == "SUCCESS") {
                            this.list.splice(this.list.indexOf(order), 1);
                            this.nativeProvider.showToastFormI4("删除成功");
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

    doRefresh(refresher) {
        // this.ionViewWillEnter();
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

    /*字段转换*/
    private transform(data) {
        for (let i = 0; i < data.length; i++) {
            data[i].orderStateMsg = ["", "等待支付", "等待发货", "等待收货", "交易完成", "交易关闭"][data[i].orderState];
            if (data[i].orderState == 4 && data[i].commentState == 1 && data[i].afterSaleState == 1) {
                data[i].orderStateMsg = "待评价";
            }
            if (data[i].afterSaleState == 2) {
                data[i].orderStateMsg = "申请售后中";
            }
            data[i].productList = this.nativeProvider.productListChange(data[i].saleOrderItems);
        }
    }


}
