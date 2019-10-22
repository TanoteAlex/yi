import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from '../http-service/http-service';

@Injectable({
    providedIn: 'root'
})
export class CouponService extends HttpServiceProvider {

    constructor(public http: HttpClient, public appConfig: AppConfig, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "coupon");
    }

    queryCouponReceive(page) {
        return this.post('query ', page)
    }

    receiveCoupon(couponId, memberId) {
        const params = new HttpParams().set('couponId', couponId).set('memberId', memberId);
        return this.get("receiveCoupon", params);
    }

    getCoupon(couponId) {
        const params = new HttpParams().set('couponId', couponId);
        return this.get("getCouponDetail", params);
    }

    getCouponReceiveDetail(couponReceiveId) {
        const params = new HttpParams().set('couponReceiveId', couponReceiveId);
        return this.get("getCouponReceiveDetail", params);
    }

    receiveVoucher(couponReceiveId,receivedMemberId) {
        const params = new HttpParams().set('couponReceiveId', couponReceiveId).set('memberId', receivedMemberId);
        return this.get("receiveVoucher", params);
    }
}
