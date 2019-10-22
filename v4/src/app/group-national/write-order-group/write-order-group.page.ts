import {Component, OnInit} from '@angular/core';
import {ConsigneeModalPage} from "../../consignee-modal/consignee-modal.page";
import {Coupon} from "../../../domain/original/coupon.model";
import {ChooseConsigneePage} from "../../choose-consignee/choose-consignee.page";
import {NativeProvider} from "../../../services/native-service/native";
import {SaleOrder} from "../../../domain/original/sale-order.model";
import {ModalController, NavController} from "@ionic/angular";
import {ShippingAddress} from "../../../domain/original/shipping-address.model";
import {CouponModalPage} from "../../coupon-about/coupon-modal/coupon-modal.page";
import {OrderProvider} from "../../../services/order-service/order";
import {MemberProvider} from "../../../services/member-service/member";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-write-order-group',
    templateUrl: './write-order-group.page.html',
    styleUrls: ['./write-order-group.page.scss'],
})
export class WriteOrderGroupPage {
    orderProductVos: SaleOrder;
    shippingAddressVo: ShippingAddress;

    /*订单类型，普通订单undefined, openGroup,joinGroup*/
    orderType;

    ordersDetail;

    ordersData;

    constructor(public memberProvider: MemberProvider, public orderProvider: OrderProvider, public navCtrl: NavController, public modalCtrl: ModalController,
                public router: ActivatedRoute, public nativeProvider: NativeProvider) {
        if (this.router.params['value'].orderType) {
            this.orderType = this.router.params['value'].orderType
        }
        if (this.router.params['value'].data) {
            this.ordersData = JSON.parse(this.router.params['value'].data);
        }
    }

    ionViewWillEnter() {
        this.getOrder(this.router.params['value'].data);
    }

    async goAddConsignee() {
        const modalAddConsignee = await this.modalCtrl.create({
            component: ConsigneeModalPage,
            componentProps: {isOrder: true},
        });
        await modalAddConsignee.present();
        await modalAddConsignee.onDidDismiss().then(data => {
            if (data.data != undefined) {
                this.shippingAddressVo = data.data;
                this.ordersData.shippingAddress = data.data;
                this.getOrder(this.ordersData);
            }
        })
    }

    async goChooseConsignee() {
        const modalChooseConsignee = await this.modalCtrl.create({
            component: ChooseConsigneePage,
            componentProps: {shippingAddress: this.shippingAddressVo},
        });
        await modalChooseConsignee.present();
        await modalChooseConsignee.onDidDismiss().then(data => {
            if (data.data != undefined && this.shippingAddressVo != data.data) {
                this.shippingAddressVo = data.data;
                this.ordersData.shippingAddress = data.data;
                this.getOrder(this.ordersData);
            }
        })
    }

    useBalance() {
        setTimeout(() => {
            this.ordersDetail.useBalance = !this.ordersDetail.useBalance;
            this.count()
        }, 100);
    }


    async goMyCoupon(couponList) {
        if (!couponList || !couponList.length) {
            return
        }
        const modalCouponOrder = await this.modalCtrl.create({
            component: CouponModalPage,
            componentProps: {couponList: couponList, type: "coupon"}
        });
        await modalCouponOrder.present();
        await modalCouponOrder.onDidDismiss().then(data => {
            let total = 0;
            this.ordersDetail.coupons = data.data;
            this.ordersDetail.coupons.forEach(e => {
                if (e.checked) {
                    total += e.parValue;
                }
            });
            this.ordersDetail.couponAmount = total;
            this.count();
        })
    }

    async goMyStorage(couponList) {
        if (!couponList || !couponList.length) {
            return
        }
        const modalCouponOrder = await this.modalCtrl.create({
            component: CouponModalPage,
            componentProps: {couponList: couponList, type: "storage"}
        });
        await modalCouponOrder.present();
        await modalCouponOrder.onDidDismiss().then(data => {
            let total = 0;
            this.ordersDetail.vouchers = data.data;
            this.ordersDetail.vouchers.forEach(e => {
                if (e.checked) {
                    total += e.parValue;
                }
            });
            this.ordersDetail.voucherAmount = total;
            this.count()
        })
    }


    count() {
        this.ordersDetail.payAmount = this.ordersDetail.saleOrderItems.map(e => {
            return e.price * e.quantity;
        }).reduce((a, b) => {
            return a + b
        }) + this.ordersDetail.freight - this.ordersDetail.couponAmount - this.ordersDetail.voucherAmount;

        if (this.ordersDetail.useBalance) {
            this.ordersDetail.payAmount -= this.ordersDetail.balance
        }
    }

    //防止重复提交
    private submitFlag = false;

    goOrderPay() {
        if (this.submitFlag) {
            this.nativeProvider.showToastFormI4("正在提交中");
            return;
        }
        this.submitFlag = true;

        if (this.shippingAddressVo == undefined || this.shippingAddressVo == null) {
            this.nativeProvider.showToastFormI4("请填写地址");
            return;
        }

        let saleOrderBo = {
            member: {id: MemberProvider.getLoginMember().id},
            shippingAddress: {id: this.shippingAddressVo.id},
            saleOrderItems: this.ordersDetail.saleOrderItems,
            orderType: this.ordersDetail.orderType,
            groupId: this.ordersDetail.groupId,
            groupBuyProduct: {id: this.ordersDetail.groupBuyProductId},
            openGroupBuy: {},
            orderSource: 1,
            groupOrderType: "",
            coupons: this.ordersDetail.coupons,
            vouchers: this.ordersDetail.vouchers,
            useBalance:this.ordersDetail.useBalance,
        };

        if (this.orderType == "openGroup") {
            saleOrderBo.groupOrderType = "1";
        }
        if (this.orderType == "joinGroup") {
            saleOrderBo.openGroupBuy = {id: this.ordersDetail.openGroupBuyId};
            saleOrderBo.groupOrderType = "2";
        }
        this.submitOrder(saleOrderBo);
    }

    submitOrder(saleOrderBo) {
        this.orderProvider.submitGroupOrder(saleOrderBo).then(e => {
                let ids = [];
                if (e.result == "SUCCESS") {
                    ids = e.data.orderIds;
                    if (!e.data.payAmount) { //余额全款支付，payAmount返回0或null
                        this.navCtrl.navigateForward(["PaymentFinishPage", {totalAmount: e.data.payAmount, ids: ids, orderType: this.orderType, payType: "余额支付"}]);
                    } else {
                        this.navCtrl.navigateForward(["PaymentOrderPage", {ids: ids, orderType: this.orderType}]);
                    }
                    this.submitFlag = false;
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                    this.submitFlag = false;
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
                this.submitFlag = false;
            }
        )
    }

    getOrder(data) {
        this.orderProvider.confirmGroupOrder(data).then(e => {
            if (e.result == "SUCCESS") {
                this.ordersDetail = e.data;
                this.shippingAddressVo = this.ordersDetail.shippingAddress;
                this.ordersDetail.productList = this.nativeProvider.productListChange(this.ordersDetail.saleOrderItems);
            } else {
                this.nativeProvider.showToastFormI4(e.message);
                setTimeout(() => {
                    this.navCtrl.goBack();
                }, 1000)
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            setTimeout(() => {
                this.navCtrl.goBack();
            }, 1000)
        })
    }


}
