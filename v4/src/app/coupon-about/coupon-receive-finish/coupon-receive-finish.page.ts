import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavController} from "@ionic/angular";
import {CouponService} from "../../../services/coupon-service/coupon.service";
import {COUPON_TYPE} from "../../Constants";

@Component({
    selector: 'app-coupon-receive-finish',
    templateUrl: './coupon-receive-finish.page.html',
    styleUrls: ['./coupon-receive-finish.page.scss'],
})
export class CouponReceiveFinishPage {

    coupon;

    ticket;

    list = ["您可登陆账户并在【我的】>【优惠券】处查看获得的优惠券", "优惠券最终解释权归壹壹平台所有"];

    constructor(public navCtrl: NavController, public route: ActivatedRoute, public couponProvider: CouponService) {

    }

    ionViewWillEnter() {
        this.couponProvider.getCoupon(this.route.params["value"].couponId).then(e => {
            this.ticket = e.data;
            this.coupon = this.transform(e.data);
        })
    }

    goHome() {
        this.navCtrl.navigateForward('/app/tabs/home')
    }

    gotoUse() {
        if (Array.isArray(this.ticket.commodities) && this.ticket.commodities.length){
            this.navCtrl.navigateForward(["CommoditiesListPage", {couponId: this.coupon.id}])
        } else{
            this.navCtrl.navigateForward("/app/tabs/home")
        }
    }

    transform(couponVo) {
        return {
            id: couponVo.id,
            state: "",
            parValue: couponVo.parValue,
            useConditionType: couponVo.useConditionType,
            fullMoney: couponVo.fullMoney,
            fullQuantity: couponVo.fullQuantity,
            validDays: "",
            startTime:couponVo.startTime,
            endTime: couponVo.endTime,
            fixedDay: couponVo.fixedDay,
            couponType: COUPON_TYPE[couponVo.couponType],
            receiveState: couponVo.receiveState,
            feelWord: couponVo.feelWord,
        };
    }
}
