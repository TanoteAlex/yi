import {Component, Input, OnInit} from '@angular/core';
import {Events, ModalController, NavController} from "@ionic/angular";
import {WechatService} from "../../../../services/wechat-service/wechat.service";
import {SELECT_COUPON} from "../../../Constants";
import {WechatforAppService} from "../../../../services/forApp-service/wechat.service";
import {AppConfig} from "../../../app.config";
import {MemberProvider} from "../../../../services/member-service/member";

@Component({
    selector: 'coupon-storage',
    templateUrl: './coupon-storage.component.html',
    styleUrls: ['./coupon-storage.component.scss']
})
export class CouponStorageComponent implements OnInit {
    @Input()
    ticket: any;
    //
    // @Input()
    // getTerm: any;
    //
    // @Input()
    // getConditionContent: any;

    @Input()
    gotoUse: any;

    @Input()
    sendOthers: any;

    @Input()
    disable: boolean;

    constructor(public navCtrl: NavController, public wechatProvider: WechatService, public modalCtrl: ModalController, public events: Events,public appConfig: AppConfig, public wechatforAppService: WechatforAppService) {

    }

    ngOnInit() {
    }

    initShareData(ticket, isShowModal = true) {
        let memberId;
        if (MemberProvider.isLogin()) {
            memberId = MemberProvider.getLoginMember().id
        }
        let shareData = {
            title: '收到一张优惠券',
            desc: "您的好友送您一张" + ticket.parValue + "元券，赶紧领取使用吧~",
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?CouponSendPage&ticketId=' + ticket.id + '&preMemberId=' + memberId + '?'),
            imgUrl: this.appConfig.shareURL + 'assets/imgs/服务中心logo.png',
        };
        /**
         * 平台
         */
        this.wechatforAppService.shareDate(shareData, isShowModal)
    }

    // select(ticketId){
    //     this.events.publish(SELECT_COUPON, ticketId);
    // }

    /*使用条件提示*/
    getConditionContent(useConditionType, fullMoney, fullQuantity) {
        switch (useConditionType) {
            case 0 :
                return "&nbsp;";
            case 1 :
                return "满" + fullMoney + "元可用";
            case 2 :
                return "满" + fullQuantity + "件可用";
            default:
                return
        }
    }

    /*有效时间提示*/
    getTerm(endTime, fixedDay?) {
        if (endTime) {
            return endTime.substr(0, 10).replace(/-/g,'.');
        }
        if (fixedDay) {
            return "有效天数" + fixedDay + "天";
        }
    }

    getTime(time) {
        return time.substr(0, 10).replace(/-/g,'.');
    }

}
