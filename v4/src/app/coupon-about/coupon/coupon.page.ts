import {Component, OnInit} from '@angular/core';
import {NativeProvider} from "../../../services/native-service/native";
import {ModalController, NavController} from "@ionic/angular";
import {MemberProvider} from "../../../services/member-service/member";
import {CouponReceive} from "../../../domain/original/coupon-receive.model";
import {BannerProvider} from "../../../services/banner-service/banner";
import {PageQuery} from "../../../util/page-query.model";
import {COUPON_TYPE} from "../../Constants";
import {WechatService} from "../../../services/wechat-service/wechat.service";
import {AppConfig} from "../../app.config";
import {WechatforAppService} from "../../../services/forApp-service/wechat.service";
import {CouponClickModalPage} from "../coupon-click-modal/coupon-click-modal.page";

@Component({
    selector: 'app-coupon',
    templateUrl: './coupon.page.html',
    styleUrls: ['./coupon.page.scss'],
})
export class CouponPage implements OnInit {

    pet = "canuse";

    /*从用户中心进入时传入true*/
    check: string = 'true';
    couponVo: CouponReceive[] = [];

    /*子组件字段对应字段*/
    tickets = [];

    advertisingCommodity;

    isLoading: boolean = false;

    pageQuery: PageQuery = new PageQuery();

    filterSet = {
        isShowFilter: false,
        filterCondition: '',
        option: [{
            name: "种类",
            isSelected: false,
            sortName: 'couponType'
        }, {
            name: "金额",
            isSelected: false,
            sortName: 'parValue'
        }, {
            name: "日期",
            isSelected: false,
            sortName: 'endTime'
        }],
    };

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public bannerProvider: BannerProvider,
                public wechatProvider: WechatService, public modalCtrl: ModalController, public appConfig: AppConfig, public wechatforAppService: WechatforAppService) {

    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.listFilter(this.pet);
        this.getAdvertisingCommodityData();
    }

    listFilter(type) {
        if (this.filterSet.isShowFilter) this.filterSet.isShowFilter = false;

        this.pageQuery.resetRequests();
        this.pageQuery.addFilter('member.id', MemberProvider.getLoginMember().id, 'eq');
        this.pageQuery.addFilter('couponType', 2, 'lte');
        this.queryFilter(type);
        this.getData();
    }

    queryFilter(type) {
        switch (type) {
            case "canuse":
                this.pageQuery.addFilter('state', 1, 'eq');
                break;
            case "already":
                this.pageQuery.addFilter('state', 2, 'eq');
                break;
            case "expired":
                this.pageQuery.addFilter('state', 3, 'eq');
                break;
            default:
                break;
        }
    }

    private getData() {
        this.tickets = [];
        this.isLoading = true;
        this.addSort();
        this.memberProvider.queryCoupon(this.pageQuery.requests).then(data => {
            this.couponVo = data.data.content;
            this.pageQuery.covertResponses(data.data);
            this.tickets = this.transform(this.couponVo);
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    filterCoupon() {
        this.filterSet.isShowFilter = !this.filterSet.isShowFilter;
    }

    selectOption(item) {
        this.filterSet.option.forEach(e => {
            e.isSelected = item.name == e.name;
        });
        this.getData();
    }

    goCouponReceive() {
        this.navCtrl.navigateForward("CouponReceivePage")
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

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.memberProvider.queryCoupon(this.pageQuery.requests).then(data => {
                    this.tickets = this.tickets.concat(this.transform(data.data.content));
                    this.pageQuery.covertResponses(data.data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

    transform(couponVo) {
        return couponVo.map(e => {
            return {
                id: e.id,
                state: ["", "coupon-canuse", "coupon-already", "coupon-expired"][e.state],
                parValue: e.parValue,
                useConditionType: e.coupon.useConditionType,
                fullMoney: e.coupon.fullMoney,
                fullQuantity: e.coupon.fullQuantity,
                validDays: e.validDays,
                startTime:e.startTime,
                endTime: e.endTime,
                couponType: COUPON_TYPE[e.coupon.couponType],
                couponId: e.coupon.id,
                feelWord:e.feelWord,
            };
        });
    }

    addSort() {
        let selectedSet = this.filterSet.option.filter(e => e.isSelected);
        if (selectedSet.length == 1) {
            this.pageQuery.onlySort(selectedSet[0].sortName, 'asc');
        }
        return;
    }

    async showRule(ticket){
        if (ticket.couponType == COUPON_TYPE[2] && ticket.state == "coupon-canuse") {
            const modal = await this.modalCtrl.create({
                    component: CouponClickModalPage,
                    componentProps: {
                        price:ticket.parValue,
                        startTime:ticket.startTime,
                        endTime:ticket.endTime
                    }
                }
            );
            await modal.present();
        }
    }
}
