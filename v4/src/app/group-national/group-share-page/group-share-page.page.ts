import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderProvider} from "../../../services/order-service/order";
import {NativeProvider} from "../../../services/native-service/native";
import {ActivatedRoute} from "@angular/router";
import {TimeCountComponent} from "../../components/time-count/time-count.component";
import {MemberProvider} from "../../../services/member-service/member";
import {ModalController, NavController} from "@ionic/angular";
import {ShareClickModalPage} from "../../share-modal/share-click-modal/share-click-modal.page";
import {WechatService} from "../../../services/wechat-service/wechat.service";
import {WechatforAppService} from "../../../services/forApp-service/wechat.service";
import {AppConfig} from "../../app.config";

const DEFAULTAVATER = "../../assets/app_icon/new_commodity/group_member_null@2x.png";

@Component({
    selector: 'app-group-share-page',
    templateUrl: './group-share-page.page.html',
    styleUrls: ['./group-share-page.page.scss'],
})
export class GroupSharePagePage implements OnInit {
    shareData;

    orderDetail;

    /**
     * 分享状态，1可参团，2团已满，3自己进入
     */
    shareState: number = 1;

    avater = [];
    @ViewChild('timeCount') timeCount: TimeCountComponent;

    constructor(public orderProvider: OrderProvider, public nativeProvider: NativeProvider, public route: ActivatedRoute, public memberProvider: MemberProvider,
                public navCtrl: NavController, public modalCtrl: ModalController, public wechatProvider: WechatService,public appConfig: AppConfig,
                public wechatforAppService: WechatforAppService) {
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
        this.nativeProvider.showLoadingForI4().then(() => {
            this.orderProvider.getOrder(this.route.params["value"].id).then(e1 => {
                this.nativeProvider.hideLoadingForI4();
                this.orderDetail = e1.data;
                this.getJoinMember();
                this.getShareState();
                if (this.shareState != 2) {
                    this.timeCount.setEndTime(this.orderDetail.groupBuyOrder.endTime);
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
            });
        });
    }

    goAddGroup() {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }

        let shoppingCartBo = {
            member: {id: MemberProvider.getLoginMember().id},
            openGroupBuy: {id: this.orderDetail.groupBuyOrder.id},
            groupOrderType: "2",
            groupBuyProduct: {id: this.orderDetail.groupBuyProductId},
            quantity: "1",
        };

        this.navCtrl.navigateForward(["WriteOrderGroupPage", {data: JSON.stringify(shoppingCartBo), orderType: "joinGroup"}], false);
    }

    async goInvitesOthers() {
        this.initShareData();
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
        this.wechatforAppService.shareDate(shareData,isShowModal)
    };

    goHome() {
        this.navCtrl.navigateForward('/app/tabs/home', false,);
    }

    getJoinMember() {
        this.avater.push(this.orderDetail.groupBuyOrder.member.avater);
        this.orderDetail.groupBuyOrder.joinGroupBuys.forEach(e => {
            this.avater.push(e.member.avater);
        });
        while (this.avater.length < this.orderDetail.groupBuyOrder.groupNum) {
            this.avater.push(DEFAULTAVATER);
        }
    }

    getShareState() {
        if (this.orderDetail.groupBuyOrder.joinNum == this.orderDetail.groupBuyOrder.groupNum) {
            this.shareState = 2;
        }
        if (!MemberProvider.isLogin()) {
            return
        }
        if (this.orderDetail.groupBuyOrder.member.id == MemberProvider.getLoginMember().id) {
            this.shareState = 3;
        }
    }

}
