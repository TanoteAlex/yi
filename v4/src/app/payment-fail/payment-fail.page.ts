import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ModalController, NavController} from "@ionic/angular";
import {GroupShareModalPage} from "../group-national/group-share-modal/group-share-modal.page";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {AppConfig} from "../app.config";

@Component({
    selector: 'app-payment-fail',
    templateUrl: './payment-fail.page.html',
    styleUrls: ['./payment-fail.page.scss'],
})
export class PaymentFailPage {
    list = ["壹壹优选不会以订单异常、系统升级为由要求您点击任何网址链接进行退款操作。"];

    totalAmount;
    back = true;

    ids = [];

    /*订单类型，普通订单undefined, openGroup,joinGroup*/
    orderType;

    constructor(public route: ActivatedRoute, public navCtrl: NavController, public router: ActivatedRoute, public modalCtrl: ModalController,
                public weChatProvider: WechatService, public nativeProvider: NativeProvider, public appConfig: AppConfig) {

        if (this.router.params['value'].orderType) {
            this.orderType = this.router.params['value'].orderType
        }

        if (this.router.params['value'].ids) {
            this.ids = this.router.params['value'].ids
        }
    }

    ionViewWillEnter() {
        this.totalAmount = this.route.params["value"].totalAmount;
    }

    goHome() {
        this.navCtrl.navigateForward("/app/tabs/home");
    }

    goOrderDetail() {
        this.navCtrl.navigateForward("OrderDetailPage");
    }

    goDeliverMyOrder() {
        this.navCtrl.navigateForward(["MyOrderPage", {state: "unpaid", goBackPage: "customerCenter"}])
    }

    //防止重复提交
    private submitFlag = false;

    goPayAgain() {
        if (this.appConfig.isApp) {
            this.navCtrl.navigateForward(["PaymentOrderPage", {ids: this.ids, orderType: this.orderType}]);
        } else {
            if (this.submitFlag) {
                this.nativeProvider.showToastFormI4("正在提交中");
                return;
            }
            this.submitFlag = true;

            let weChatVo = {
                memberId: MemberProvider.getLoginMember().id,
                orderIds: this.ids,
                openId: MemberProvider.getLoginMember().openId,
                totalFee: this.totalAmount
            };

            this.weChatProvider.readyPay(weChatVo).then(e => {
                this.submitFlag = false;
                if (e.result == "SUCCESS") {
                    this.weChatProvider.pay(e.data, () => {
                        this.navCtrl.navigateForward(["PaymentFinishPage", {
                            totalAmount: this.totalAmount,
                            orderType: this.orderType
                        }])
                    }, () => {
                        return
                    }, () => {
                        return
                    })
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                }
            }, err => {
                this.submitFlag = false;
                this.nativeProvider.showToastFormI4(err.message);
            });
        }
    }


}
