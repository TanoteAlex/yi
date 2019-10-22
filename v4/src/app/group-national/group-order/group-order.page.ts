import {Component, OnInit} from '@angular/core';
import {WechatService} from "../../../services/wechat-service/wechat.service";
import {REFRESH_CUSTOMERCENTER, REFRESH_SHOPPINGCART, SUCCESS} from "../../Constants";
import {CommodityProvider} from "../../../services/commodity-service/commodity";
import {MemberProvider} from "../../../services/member-service/member";
import {OrderProvider} from "../../../services/order-service/order";
import {AlertController, Events, NavController} from "@ionic/angular";
import {customOnError, customOnResult} from "../../../services/http-service/http-service";
import {CartService} from "../../../services/member-service/cart.service";
import {Order} from "../../../domain/original/order.model";
import {NativeProvider} from "../../../services/native-service/native";
import {PageQuery} from "../../../util/page-query.model";
import {ActivatedRoute} from "@angular/router";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";

@Component({
    selector: 'app-group-order',
    templateUrl: './group-order.page.html',
    styleUrls: ['./group-order.page.scss'],
})
export class GroupOrderPage implements OnInit {
    state = "all";

    list: Order[] = [];

    pageQuery: PageQuery = new PageQuery();

    isLoading: boolean = false;

    /**
     * 点击返回是，返回的页面
     * 这里正常为undefined，一般传入customerCenter，
     */
    goBackPage: string;

    constructor(public nativeProvider: NativeProvider, public route: ActivatedRoute, public alertCtrl: AlertController, public memberProvider: MemberProvider,
                public orderProvider: OrderProvider, public navCtrl: NavController, public commodityProvider: CommodityProvider, public events: Events,
                public weChatProvider: WechatService, public cartProvider: CartService, public groupActiveProvider: GroupActiveService) {
        /*unpaid或deliver入口进入*/
        if (this.route.params["value"].state != 'undefined') {
            this.state = this.route.params["value"].state;
        }
        this.goBackPage = this.route.params["value"].goBackPage;
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.listFilter(this.state);
    }

    ionViewWillLeave() {
        this.events.publish(REFRESH_CUSTOMERCENTER);
    }

    listFilter(type) {
        this.pageQuery.resetRequests();
        this.queryFilter(type);
        this.getData(this.pageQuery);
    }

    /*分割导航，不同分页请求*/
    queryFilter(type) {
        switch (type) {
            case "unpaid":
                this.pageQuery.addOrFilter('state', 1, 'eq');
                break;
            case "grouping":
                this.pageQuery.addOrFilter('state', 2, 'eq');
                break;
            case "grouped":
                this.pageQuery.addOrFilter('state', 3, 'eq');
                break;
            case "canceled":
                this.pageQuery.addOrFilter('state', 4, 'eq');
                break;
            default:
                break;
        }
    }

    private getData(page: PageQuery) {
        this.list = [];
        this.isLoading = true;
        this.groupActiveProvider.queryMyGroupOrder(page.requests).then(data => {
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
        this.navCtrl.navigateForward(["GroupOrderDetailPage", {orderId: order.id}], false);
    }

    goGroupShare(order) {
        this.navCtrl.navigateForward(["GroupSharePagePage", {id: order.id}]);
    }

    goOrderPay(order) {
        let weChatVo = {
            memberId: MemberProvider.getLoginMember().id,
            orderIds: [order.id],
            openId: MemberProvider.getLoginMember().openId,
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
                                this.ionViewWillEnter();
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

    goEvaluation(order) {
        this.navCtrl.navigateForward(["EvaluationPage", {orderId: order.id}])
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
        this.ionViewWillEnter();
        this.groupActiveProvider.queryMyGroupOrder(this.pageQuery.requests).then(data => {

            this.transform(data.content);
            this.pageQuery.covertResponses(data);
            this.list = data.content;
            refresher.target.complete();
        }, err => refresher.target.complete())
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.groupActiveProvider.queryMyGroupOrder(this.pageQuery.requests).then(data => {

                    this.transform(data.content);
                    this.list = this.list.concat(data.content);

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
            data[i].orderStateMsg = ["", "等待支付", "拼团中", "拼团成功", "未成团"][data[i].state];
            data[i].productList = this.nativeProvider.productListChange(data[i].saleOrder.saleOrderItems);
            data[i].productList.forEach((e1, i1) => {
                e1.price = data[i].saleOrder.saleOrderItems[i1].price
            })
        }
    }


}
