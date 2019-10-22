import {Component, OnInit} from '@angular/core';
import {MemberProvider} from "../../../services/member-service/member";
import {ActivatedRoute} from "@angular/router";
import {COUPON_TYPE, SUCCESS} from "../../Constants";
import {CouponService} from "../../../services/coupon-service/coupon.service";
import {ModalController, NavController} from "@ionic/angular";
import {NativeProvider} from "../../../services/native-service/native";
import {WechatService} from "../../../services/wechat-service/wechat.service";
import {ShareClickModalPage} from "../../share-modal/share-click-modal/share-click-modal.page";
import {WechatforAppService} from "../../../services/forApp-service/wechat.service";
import {AppConfig} from "../../app.config";

@Component({
    selector: 'app-coupon-send',
    templateUrl: './coupon-send.page.html',
    styleUrls: ['./coupon-send.page.scss'],
})
export class CouponSendPage implements OnInit {
    shareData;

    ticketId;

    coupon;
    ticket;

    /**
     * 领取状态，1未领取，2已领取，3自己进入
     */
    receivedState: number = 1;

    constructor(public memberProvider: MemberProvider, public route: ActivatedRoute, public couponProvider: CouponService, public navCtrl: NavController,
                public nativeProvider: NativeProvider, public wechatProvider: WechatService,public modalCtrl: ModalController,public appConfig: AppConfig,
                public wechatforAppService: WechatforAppService) {
        this.ticketId = this.route.params["value"].ticketId
    }

    ngOnInit() {
        //点击分享进来，获取分享参数
        let href = window.location.href;
        if (href.indexOf('unionId') != -1) {
            this.shareData = {
                preMemberId: href.split('preMemberId=')[1].split('?')[0],
                openId: href.split('openId=')[1].split('&')[0],
                unionId: href.split('unionId=')[1],
            };
            this.memberProvider.initLoginSession();
            this.memberProvider.autoLogin(href);
        } else {
            this.shareData = null;
        }
    }

    ionViewWillEnter() {
        this.couponProvider.getCouponReceiveDetail(this.ticketId).then(e => {
            if (e.result == SUCCESS) {
                this.ticket = e.data;
                this.coupon = this.transform(e.data);
                this.getReceivedState();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        })
    }

    goReceive() {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }

        this.couponProvider.receiveVoucher(this.ticketId, MemberProvider.getLoginMember().id).then(e => {
            if (e.result == "SUCCESS") {
                this.navCtrl.navigateForward(["CouponReceiveFinishPage", {couponId: this.ticket.coupon.id}], false)
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        })
    }

    goHome() {
        this.navCtrl.navigateForward('/app/tabs/home', false,);
    }

    async sendOthers(ticket) {
        this.initShareData(ticket);
        /*const modal = await this.modalCtrl.create({
                component: ShareClickModalPage,
            }
        );
        await modal.present();*/
    }

    initShareData(ticket, isShowModal = true) {
        let memberId;
        if (MemberProvider.isLogin()) {
            memberId = MemberProvider.getLoginMember().id
        }
        let shareData = {
            title: '收到一张'+["","优惠券","代金券"][ticket.couponType],
            desc: "您的好友送您一张" + ticket.parValue + "元券，赶紧领取使用吧~",
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?CouponSendPage&ticketId=' + ticket.id + '&preMemberId=' + memberId + '?'),
            imgUrl: this.appConfig.shareURL + 'assets/imgs/服务中心logo.png',
        };
        /**
         * 平台
         */
        this.wechatforAppService.shareDate(shareData,isShowModal)
    };

    transform(couponVo) {
        return {
            id: couponVo.id,
            state: "",
            parValue: couponVo.parValue,
            useConditionType: couponVo.coupon.useConditionType,
            fullMoney: couponVo.coupon.fullMoney,
            fullQuantity: couponVo.coupon.fullQuantity,
            validDays: couponVo.validDays,
            startTime:couponVo.startTime,
            endTime: couponVo.endTime,
            couponType: COUPON_TYPE[couponVo.coupon.couponType],
            couponId: couponVo.coupon.id,
            feelWord: couponVo.feelWord,

        };
    }

    getReceivedState() {
        if (MemberProvider.isLogin() && MemberProvider.getLoginMember().id == this.ticket.member.id) {
            this.receivedState = 3;
        }
        if (this.ticket.state == 3) {
            this.receivedState = 2;
        }
    }

}
