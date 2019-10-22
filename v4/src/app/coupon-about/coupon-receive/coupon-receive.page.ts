import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";
import {PageQuery} from "../../../util/page-query.model";
import {CouponService} from "../../../services/coupon-service/coupon.service";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";
import {COUPON_TYPE} from "../../Constants";
import {BannerProvider} from "../../../services/banner-service/banner";

@Component({
    selector: 'app-coupon-receive',
    templateUrl: './coupon-receive.page.html',
    styleUrls: ['./coupon-receive.page.scss'],
})
export class CouponReceivePage {
    /*子组件对应字段*/
    tickets = [];

    pageQuery: PageQuery = new PageQuery();

    ableCoupon = [];
    unableCoupon = [];

    advertisingCommodity;

    constructor(public navCtrl: NavController, public couponProvider: CouponService, public nativeProvider: NativeProvider, public bannerProvider: BannerProvider,) {
    }

    ionViewWillEnter() {
        this.getData(this.pageQuery.requests)
    }

    goCouponReceiveFinish(ticket) {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward("LoginPage");
        }
        this.couponProvider.receiveCoupon(ticket.id, MemberProvider.getLoginMember().id).then(e => {
            if (e.result == "SUCCESS") {
                this.navCtrl.navigateForward(["CouponReceiveFinishPage", {couponId: ticket.id}], false)
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        })
    }

    getData(page) {
        if (MemberProvider.isLogin()) {
            this.pageQuery.pushParamsRequests("member.id", MemberProvider.getLoginMember().id)
        }

        this.couponProvider.queryCouponReceive(page).then(e => {
            this.tickets = this.transform(e.content);
            this.getCoupon();
            this.getAdvertisingCommodityData();
            this.pageQuery.covertResponses(e);
        }, err => {
            this.nativeProvider.showToastFormI4(err.message)
        })
    }

    /*广告*/
    getAdvertisingCommodityData() {
        this.bannerProvider.getAdvertisingCommodity(6).then(data => {
            if (data.result == "SUCCESS") {
                this.advertisingCommodity = data.data;
            } else {
                this.nativeProvider.showToastFormI4(data.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    getCoupon() {
        this.ableCoupon = [];
        this.unableCoupon = [];
        this.tickets.forEach(e => {
            if (!e.receiveState) {
                this.ableCoupon.push(e);
            } else {
                this.unableCoupon.push(e);
            }
        })
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();

            this.couponProvider.queryCouponReceive(this.pageQuery.requests).then(e => {
                this.tickets = this.tickets.concat(this.transform(e.content));
                this.getCoupon();
                this.pageQuery.covertResponses(e);
                infiniteScroll.target.complete();
            }, err => {
                infiniteScroll.target.complete();
            })
        } else {
            infiniteScroll.target.complete();
        }
    }

    transform(couponVo) {
        return couponVo.map(e => {
            return {
                id: e.id,
                state: "",
                couponName:e.couponName,
                parValue: e.parValue,
                useConditionType: e.useConditionType,
                fullMoney: e.fullMoney,
                fullQuantity: e.fullQuantity,
                validDays: "",
                endTime: e.endTime,
                fixedDay: e.fixedDay,
                couponType: COUPON_TYPE[e.couponType],
                receiveState: e.receiveState,
                feelWord: e.feelWord,
            };
        });
    }

}
