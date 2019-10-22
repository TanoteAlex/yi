import {Component} from '@angular/core';
import {ShippingAddress} from "../../domain/original/shipping-address.model";
import {SaleOrder} from "../../domain/original/sale-order.model";
import {MemberProvider} from "../../services/member-service/member";
import {OrderProvider} from "../../services/order-service/order";
import {ModalController, NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {NativeProvider} from "../../services/native-service/native";
import {ChooseConsigneePage} from "../choose-consignee/choose-consignee.page";
import {ConsigneeModalPage} from "../consignee-modal/consignee-modal.page";
import {CouponModalPage} from "../coupon-about/coupon-modal/coupon-modal.page";
import set = Reflect.set;

@Component({
    selector: 'app-write-order',
    templateUrl: './write-order.page.html',
    styleUrls: ['./write-order.page.scss'],
})
export class WriteOrderPage {
    shippingAddressVo: ShippingAddress;

    /*订单类型，普通订单undefined, openGroup,joinGroup,团购不进这里。另外的地方*/
    orderType = undefined;

    ordersDetail;

    /**
     * 专门存放订单中使用了的优惠券id
     * 防止多个分割订单同时使用一张优惠券
     */
    usedCouponId = [];

    constructor(public memberProvider: MemberProvider, public orderProvider: OrderProvider, public navCtrl: NavController, public modalCtrl: ModalController,
                public router: ActivatedRoute, public nativeProvider: NativeProvider) {
    }

    ionViewWillEnter() {
        this.getOrder(this.router.params['value'].shoppingCart);
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
                this.ordersDetail.shippingAddress = data.data;
                this.getOrder(this.ordersDetail);
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
                this.ordersDetail.shippingAddress = data.data;
                this.getOrder(this.ordersDetail);
            }
        })
    }

    async goMyCoupon(couponList, i, splitPrice, isUseBalance) {
        if (!this.getCouponsNum(i)) {
            return
        }
        const modalCouponOrder = await this.modalCtrl.create({
            component: CouponModalPage,
            componentProps: {
                couponList: couponList, type: "coupon", index: i, splitPrice: splitPrice, leaveBalance: this.countLeaveBalance(i),
                isUseBalance: isUseBalance, leavePrice: this.getLeavePrice(i, "voucher")
            }
        });
        await modalCouponOrder.present();
        await modalCouponOrder.onDidDismiss().then(data => {
            if (!data.data) {
                return;
            }
            let total = 0;
            this.ordersDetail.splitOrders[i].coupons = data.data;
            this.ordersDetail.splitOrders[i].coupons.forEach(e => {
                if (e.checked) {
                    this.flagCoupons(e.id, i);
                    total += e.parValue;
                }
            });
            this.ordersDetail.splitOrders[i].couponAmount = total;
            this.count(i);
        })
    }

    async goMyStorage(couponList, i, splitPrice, isUseBalance) {
        if (!couponList || !couponList.length) {
            return
        }
        const modalCouponOrder = await this.modalCtrl.create({
            component: CouponModalPage,
            componentProps: {
                couponList: couponList, type: "storage", index: i, splitPrice: splitPrice, leaveBalance: this.countLeaveBalance(i),
                isUseBalance: isUseBalance, leavePrice: this.getLeavePrice(i, "coupon")
            }
        });
        await modalCouponOrder.present();
        await modalCouponOrder.onDidDismiss().then(data => {
            if (!data.data) {
                return;
            }
            let total = 0;
            this.ordersDetail.splitOrders[i].vouchers = data.data;
            this.ordersDetail.splitOrders[i].vouchers.forEach(e => {
                if (e.checked) {
                    total += e.parValue;
                }
            });
            this.ordersDetail.splitOrders[i].voucherAmount = total;
            this.count(i)
        })
    }

    useBalanceMethod(i) {
        setTimeout(() => {
            this.ordersDetail.splitOrders[i].useBalance = !this.ordersDetail.splitOrders[i].useBalance;
            this.count(i)
        }, 100);
    }

    useIntegralCashMethod(i) {
        setTimeout(() => {
            this.ordersDetail.splitOrders[i].useIntegralCash = !this.ordersDetail.splitOrders[i].useIntegralCash;
            this.count(i)
        }, 100);
    }

    count(i) {
        let splitOrders = this.ordersDetail.splitOrders;
        let _this = this;

        splitOrders[i].payAmount = splitOrders[i].saleOrderItems.map(e => {
            return e.price * e.quantity;
        }).reduce((a, b) => {
            return a + b
        }) + splitOrders[i].freight - splitOrders[i].couponAmount - splitOrders[i].voucherAmount;
        splitOrders[i].payAmount = Number(splitOrders[i].payAmount.toFixed(2));

        //余额的计算
        countSplitOrderBalance(i);

        //积分的计算
        countSplitOrderIntegral(i);

        //上一个余额不使用，余额量自动增加到下一个余额抵扣
        // if (!splitOrders[i].useBalance) {
        //     splitOrders.forEach((e2, i2) => {
        //         if (e2.payAmount > 0 && e2.useBalance) {
        //             countSplitOrderBalance(i2);
        //         }
        //         countSplitOrderBalance(i);
        //     });
        // }

        this.ordersDetail.splitOrders[i] = splitOrders[i];
        this.ordersDetail.payAmount = this.ordersDetail.splitOrders.map(e => {
            return e.payAmount
        }).reduce((a, b) => {
            return a + b
        }).toFixed(2);

        //余额算法
        function countSplitOrderBalance(index) {
            let leaveBalance = _this.ordersDetail.accountBalance;
            splitOrders.forEach((e1, i1) => {
                if (index != i1 && splitOrders[i1].useBalance) leaveBalance -= e1.balance;
            });
            splitOrders[index].balance = splitOrders[index].payAmount <= leaveBalance ? splitOrders[index].payAmount.toFixed(2) : leaveBalance.toFixed(2);

            if (splitOrders[index].useBalance) {
                if(splitOrders[index].payAmount >0){
                    splitOrders[index].payAmount -= splitOrders[index].balance
                }
            }else{
                splitOrders[index].balance = 0;
            }
        }

        //积分算法
        function countSplitOrderIntegral(index) {
            let leaveIntegral = _this.ordersDetail.integralAmount;
            splitOrders.forEach((e1, i1) => {
                if (index != i1 && splitOrders[i1].useIntegralCash){
                    leaveIntegral -= e1.integralCashAmount;
                }
            });
            splitOrders[index].integralCashAmount = splitOrders[index].payAmount <= leaveIntegral ? splitOrders[index].payAmount.toFixed(2) : leaveIntegral.toFixed(2);

            if (splitOrders[index].useIntegralCash) {
                if(splitOrders[index].payAmount >0){
                    splitOrders[index].payAmount -= splitOrders[index].integralCashAmount
                }
            }else{
                splitOrders[index].integralCashAmount=0;
            }
        }
    }

    /**
     * 跟上面那个一样
     * 除了第i个订单外，还剩多少余额(用于新增的需求，在优惠券模态框显示应付金额
     */
    countLeaveBalance(i) {
        let leaveBalance = this.ordersDetail.accountBalance;
        this.ordersDetail.splitOrders.forEach((e1, i1) => {
            if (i != i1 && this.ordersDetail.splitOrders[i1].useBalance) leaveBalance -= e1.balance;
        });
        return leaveBalance
    }

    //防止重复提交
    private submitFlag = false;

    goOrderPay() {
        if (this.submitFlag) {
            this.nativeProvider.showToastFormI4("正在提交中");
            return;
        }

        if (this.shippingAddressVo == undefined || this.shippingAddressVo == null) {
            this.nativeProvider.showToastFormI4("请填写地址");
            return;
        }

        let saleOrderBo = {
            member: {id: MemberProvider.getLoginMember().id},
            shippingAddress: {id: this.shippingAddressVo.id},
            splitOrders: this.ordersDetail.splitOrders,
            cartVos: this.ordersDetail.cartVos,
            orderType: this.ordersDetail.orderType,
            groupId: this.ordersDetail.groupId,
            groupBuyProduct: {},
            openGroupBuy: {},
            orderSource: 1,
            groupOrderType: ""
        };

        this.submitOrder(saleOrderBo);
    }

    submitOrder(saleOrderBo) {
        this.submitFlag = true;
        this.orderProvider.submitOrder(saleOrderBo).then(e => {
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
        console.log(data)
        this.nativeProvider.showLoadingForI4().then(() => {
            this.orderProvider.confirmOrder(data).then(e => {
                this.nativeProvider.hideLoadingForI4();
                handleData(e.data)
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
            });
        })

        const handleData = (data) => {
            this.ordersDetail = data;
            if(this.ordersDetail.shippingAddress){
                this.shippingAddressVo = this.ordersDetail.shippingAddress;
            }
            this.ordersDetail.splitOrders.forEach((e, i) => {
                e.productList = this.nativeProvider.productListChange(e.saleOrderItems);
                e.coupons.forEach(e2 => {  //标识每个分割订单所使用的优惠券
                    if (e2.checked) this.flagCoupons(e2.id, i);
                })
            })
        }
    }

    /**
     * 临时标志使用的优惠券，
     * 告诉每张制定优惠券已被使用在哪个订单上了
     */
    flagCoupons(usedCouponId, index) {
        this.usedCouponId[index] = usedCouponId;
        this.ordersDetail.splitOrders.forEach((e1, i1) => {
            e1.coupons.forEach((e2, i2) => {
                if (this.usedCouponId.some(e3 => e3 == e2.id)) {
                    this.usedCouponId.forEach((e4, i4) => {
                        if (e4 == e2.id) e2.orderNo = i4;
                    })
                } else {
                    e2.orderNo = null;
                }
            })
        });
    }

    getCouponsNum(index) {
        return this.ordersDetail.splitOrders[index].coupons.filter(e => (e.orderNo === null || e.orderNo == index)).length
    }

    /**
     * 用于优惠券modal应付的问题，
     * 计算该订单金额-代金券/优惠券 后的金额
     */
    getLeavePrice(index, couponType) {
        if (couponType == "coupon") {
            return this.ordersDetail.splitOrders[index].orderAmount - this.ordersDetail.splitOrders[index].couponAmount
        }
        if (couponType == "voucher") {
            return this.ordersDetail.splitOrders[index].orderAmount - this.ordersDetail.splitOrders[index].voucherAmount
        }
    }

}

