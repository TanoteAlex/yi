import {Component, OnInit} from '@angular/core';
import {Events, ModalController, NavParams} from "@ionic/angular";
import {COUPON_TYPE, SELECT_COUPON} from "../../Constants";

const content = document.getElementById('conent');

@Component({
    selector: 'app-coupon-modal',
    templateUrl: './coupon-modal.page.html',
    styleUrls: ['./coupon-modal.page.scss'],
})
export class CouponModalPage implements OnInit {
    coupons;
    /**
     * 第几个订单，0开始算起
     */
    index;
    /**
     * 只展示没被其他订单选择了的优惠券
     */
    showCoupons;
    type;

    /**
     * 订单应付金额，仅改页面有用
     */
    splitPrice;

    leaveBalance;
    isUseBalance: boolean;
    leavePrice;

    constructor(public modalCtrl: ModalController, public params: NavParams, public events: Events,) {
        this.coupons = this.params.data.couponList;
        this.type = this.params.data.type;
        this.index = this.params.data.index;
        this.leaveBalance = this.params.data.leaveBalance;
        this.isUseBalance = this.params.data.isUseBalance;
        this.leavePrice = this.params.data.leavePrice;

        /**
         * 监听优惠券选择事件
         */
        // this.events.subscribe(SELECT_COUPON, (ticketId) => this.select(ticketId))
    }

    ngOnInit() {
        this.showCoupons = this.getShowCoupons();
        this.splitPrice = this.getTotalSplitPrice(parseFloat(this.params.data.splitPrice));
    }

    /*select(ticketId) {
        let item = this.coupons.filter(e => e.id == ticketId)[0];
        if (this.type == "coupon") {
            this.coupons.filter(e => e != item).forEach(e1 => e1.checked = false);
        }
        item.checked = !item.checked;
    }*/

    select(item) {
        if (this.type == "coupon") {
            this.coupons.filter(e => e != item).forEach(e1 => e1.checked = false)
        }
        item.checked = !item.checked;
    }

    cancel() {
        this.coupons.forEach(e => {
            e.checked = false;
        });
        this.goBack()
    }

    goBack() {
        this.modalCtrl.dismiss(this.coupons);
    }

    getShowCoupons() {
        return this.coupons.filter(e => (e.orderNo === null || e.orderNo == this.index))
    }

    getSplitPrice() {
        let countPrice = this.coupons.reduce((pre, cur) => {
            if (cur.checked) {
                return pre + cur.parValue;
            } else {
                return pre
            }
        }, 0);
        let splitPrice = (this.splitPrice - countPrice).toFixed(2);
        if (!this.isUseBalance) {
            return splitPrice;
        } else {
            if (this.leavePrice - countPrice > this.leaveBalance) {
                return (this.leavePrice - countPrice - this.leaveBalance).toFixed(2);
            } else {
                return "0.00";
            }
        }
        /*if (this.isUseBalance && splitPrice < this.leaveBalance) {
            if (this.leavePrice - countPrice < this.leaveBalance) {
                return
            } else {
                return splitPrice;
            }
        }
        if (this.isUseBalance && splitPrice > this.leaveBalance) {
            return (Number(splitPrice) - this.leaveBalance).toFixed(2);
        }*/
    }

    getTotalSplitPrice(splitPrice: number) {
        let countPrice = this.coupons.reduce((pre, cur) => {
            if (cur.checked) {
                return pre + cur.parValue;
            } else {
                return pre
            }
        }, 0);
        return (splitPrice + countPrice).toFixed(2);
    }

    transform(couponVo) {
        return {
            id: couponVo.id,
            state: "",
            parValue: couponVo.parValue,
            useConditionType: couponVo.coupon.useConditionType,
            fullMoney: couponVo.coupon.fullMoney,
            fullQuantity: couponVo.coupon.fullQuantity,
            validDays: couponVo.validDays,
            startTime: couponVo.startTime,
            endTime: couponVo.endTime,
            couponType: COUPON_TYPE[couponVo.coupon.couponType],
            couponId: couponVo.coupon.id,
            checked: couponVo.checked,
            feelWord: couponVo.feelWord,
        };
    }

}
