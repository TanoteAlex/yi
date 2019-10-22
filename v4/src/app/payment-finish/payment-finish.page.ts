import {Component, ViewChild} from '@angular/core';
import {AlertController, ModalController, NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {TimeCountComponent} from "../components/time-count/time-count.component";
import {OrderProvider} from "../../services/order-service/order";
import {Order} from "../../domain/original/order.model";
import {NativeProvider} from "../../services/native-service/native";
import {ShareClickModalPage} from "../share-modal/share-click-modal/share-click-modal.page";
import {MemberProvider} from "../../services/member-service/member";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {WechatforAppService} from "../../services/forApp-service/wechat.service";
import {AppConfig} from "../app.config";

const DEFAULTAVATER = "../assets/app_icon/new_commodity/group_member_null@2x.png";


@Component({
    selector: 'app-payment-finish',
    templateUrl: './payment-finish.page.html',
    styleUrls: ['./payment-finish.page.scss'],
})
export class PaymentFinishPage {

    list = ["壹壹优选不会以订单异常、系统升级为由要求您点击任何网址链接进行退款操作。"];

    totalAmount;
    back = true;

    /*订单类型，普通订单undefined, openGroup,joinGroup*/
    orderType;
    ids = [];

    orderDetail: Order;
    payType;

    avater = [];

    @ViewChild('timeCount') timeCount: TimeCountComponent;

    constructor(public route: ActivatedRoute, public navCtrl: NavController, public router: ActivatedRoute, public modalCtrl: ModalController,public appConfig: AppConfig,
                public wechatforAppService: WechatforAppService, public orderProvider: OrderProvider, public nativeProvider: NativeProvider, public wechatProvider: WechatService,
                public alertCtrl: AlertController,) {
        this.orderType = this.router.params['value'].orderType;
        this.ids = this.router.params['value'].ids;
        this.payType = this.router.params['value'].payType
    }

    ionViewWillEnter() {
        this.totalAmount = this.route.params["value"].totalAmount;
        this.getRewardByPaid();

        if (this.orderType == 'openGroup') {
            this.orderProvider.getOrder(this.route.params["value"].ids).then(e1 => {
                if (e1.result == "SUCCESS") {
                    this.orderDetail = e1.data;
                    this.getJoinMember();
                    this.timeCount.setEndTime(this.orderDetail.groupBuyOrder.endTime);
                    this.initShareData(false);
                } else {
                    this.nativeProvider.showToastFormI4(e1.message);
                }
            }, err => this.nativeProvider.showToastFormI4(err.message));
        }
    }

    goHome() {
        this.navCtrl.navigateForward("/app/tabs/home");
    }

    goOrderDetail() {
        this.navCtrl.navigateForward("OrderDetailPage");
    }

    goDeliverMyOrder() {
        this.navCtrl.navigateForward(["MyOrderPage", {state: "deliver", goBackPage: "customerCenter"}])
    }

    getRewardByPaid() {
        if (this.ids) {
            this.orderProvider.getRewardByPaid(this.ids).then(e => {
                if (e.data && e.data.length) {
                    this.list = e.data.map(e => e.remind + "，该券可叠加使用，请在【我的】->【我的券】里查看。");
                }
            })
        }
    }

    async inviteFriends() {
        this.initShareData()
        /*const modal = await this.modalCtrl.create({
                component: ShareClickModalPage,
            }
        );
        await modal.present();*/
    }

    initShareData(isShowModal = true) {
        let memberId;
        if (MemberProvider.isLogin()) {
            memberId = MemberProvider.getLoginMember().id
        }
        let shareData = {
            title: "[仅剩" + (this.orderDetail.groupBuyOrder.groupNum - this.orderDetail.groupBuyOrder.joinNum) + "人]快来" + this.orderDetail.saleOrderItems[0].price + "元拼" + this.orderDetail.saleOrderItems[0].product.productShortName,
            desc: this.orderDetail.saleOrderItems[0].product.productName,
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?GroupSharePagePage&id=' + this.orderDetail.id + '&preMemberId=' + memberId + '?'),
            imgUrl: this.orderDetail.saleOrderItems[0].product.productImgPath,
        };
        /**
         * 平台
         */
        this.wechatforAppService.shareDate(shareData, isShowModal)
    };

    getJoinMember() {
        this.avater.push(this.orderDetail.groupBuyOrder.member.avater);
        this.orderDetail.groupBuyOrder.joinGroupBuys.forEach(e => {
            this.avater.push(e.member.avater);
        });
        while (this.avater.length < this.orderDetail.groupBuyOrder.groupNum) {
            this.avater.push(DEFAULTAVATER);
        }
    }


}
