import {Component, OnInit, ViewChild} from '@angular/core';
import {AttrGroupModalPage} from "../attr-group-modal/attr-group-modal.page";
import {NativeProvider} from "../../../services/native-service/native";
import {Events, ModalController, NavController} from "@ionic/angular";
import {GroupPurchaseModalPage} from "../group-purchase-modal/group-purchase-modal.page";
import {ActivatedRoute} from "@angular/router";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ServiceModalPage} from "../../commodity-modal/service-modal/service-modal.page";
import {TimeCountComponent} from "../../components/time-count/time-count.component";
import {MemberProvider} from "../../../services/member-service/member";
import {WechatService} from "../../../services/wechat-service/wechat.service";
import {ShareClickModalPage} from "../../share-modal/share-click-modal/share-click-modal.page";
import {ShareData} from "../../../domain/original/share-data.model";
import {WechatforAppService} from "../../../services/forApp-service/wechat.service";
import {AppConfig} from "../../app.config";

@Component({
    selector: 'app-commodity-group',
    templateUrl: './commodity-group.page.html',
    styleUrls: ['./commodity-group.page.scss'],
})
export class CommodityGroupPage implements OnInit {
    select: string = "introduction";

    /*拼团信息*/
    groupInfo;

    /*商品信息*/
    commodity;

    preMemberId;
    /*开团成员(前两条*/
    groupMember = [];

    //该货品规格
    attr: string = "";

    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData: ShareData;

    @ViewChild('timeCount') timeCount: TimeCountComponent;

    constructor(public wechatProvider: WechatService, public nativeProvider: NativeProvider, public navCtrl: NavController, public modalCtrl: ModalController, public route: ActivatedRoute,
                public activeProvider: GroupActiveService, public domSanitizer: DomSanitizer, public events: Events,public memberProvider: MemberProvider,
                public appConfig: AppConfig, public wechatforAppService: WechatforAppService,) {
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
        this.route.data.subscribe((data) => {
            this.groupInfo = data.data;
            this.commodity = this.groupInfo.commodity;
            this.groupMember = this.groupInfo.openGroupBuys;

            this.groupInfo.product.attributes.forEach(e => this.attr += (e.attrValue + " "));
            this.timeCount.setEndTime(this.groupInfo.endTime);
        });

        //页面跳转的初始化切换，供其他页面进入评价或详情
        if (this.route.params["value"].segment != undefined) {
            this.select = this.route.params["value"].segment;
        }

        this.initShareData(false);
    }

    //打开已开团列表
    async openPurchaseModal() {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }
        const modal = await this.modalCtrl.create({
                component: GroupPurchaseModalPage,
                componentProps: this.groupInfo
            }
        );
        await modal.present();
    }

    //开启拼团
    async goGroupWriteOrder() {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }
        const modal = await this.modalCtrl.create({
            component: AttrGroupModalPage,
            componentProps: {data: this.groupInfo, orderType: 'openGroup'},
        });
        await  modal.present()
    }

    //参与拼团
    async joinGroup(item) {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }
        const modal = await this.modalCtrl.create({
            component: AttrGroupModalPage,
            componentProps: {data: this.groupInfo, groupId: item.id, orderType: 'joinGroup'}
        });
        await modal.present()
    }

    async serviceModal() {
        const modal = await this.modalCtrl.create({
                component: ServiceModalPage,
            }
        );
        await modal.present();
    }

    onCustom($event) {
        this.select = $event;
    }

    async openShareModal() {
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
            title: this.commodity.commodityName,
            desc: this.commodity.commodityShortName,
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?CommodityGroupPage&id=' + this.groupInfo.id + "&preMemberId=" + memberId + "?"),
            imgUrl: this.commodity.imgPath,
        };
        /**
         * 平台
         */
        this.wechatforAppService.shareDate(shareData, isShowModal)
    }
}
