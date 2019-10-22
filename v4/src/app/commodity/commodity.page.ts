import {Component, ViewChild} from '@angular/core';

import {DomSanitizer} from '@angular/platform-browser';
import {SelectProduct} from "../../domain/original/select-product";
import {Commodity} from "../../domain/original/commodity.model";
import {Events, ModalController, NavController, Platform} from "@ionic/angular";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {MemberProvider} from "../../services/member-service/member";
import {ActivatedRoute} from "@angular/router";
import {AttrModalPage} from "../commodity-modal/attr-modal/attr-modal";
import {ServiceModalPage} from "../commodity-modal/service-modal/service-modal.page";
import {ParamsModalPage} from "../commodity-modal/params-modal/params-modal.page";
import {NativeProvider} from "../../services/native-service/native";
import {REFRESH_SHOPPINGCART} from "../Constants";
import {ShareModalPage} from "../share-modal/share-modal.page";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {CommodityDetailComponent} from "../components/commodity-detail/commodity-detail.component";
import {ShareData} from "../../domain/original/share-data.model";
import {ShareClickModalPage} from "../share-modal/share-click-modal/share-click-modal.page";
import {CartService} from "../../services/member-service/cart.service";
import {platformService} from "../../services/native-service/platform.service";
import {WechatforAppService} from "../../services/forApp-service/wechat.service";
import {AppConfig} from "../app.config";

/**
 * Generated class for the CommodityPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
    selector: 'page-commodity',
    templateUrl: 'commodity.page.html',
    styleUrls: ['./commodity.page.scss']
})
export class CommodityPage {

    select: string = "introduction";

    commodity: Commodity = new Commodity();

    commodityType;
    /**
     * 规格选择后获取的产品，包含selectProduct，num数量，attr已选属性名
     */
    selectProduct: SelectProduct;

    /**
     * 货品所有规格组
     */
    attrGroups = [];

    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData: ShareData;

    /**
     * 一般undefined，送礼gift
     */
    buyType;

    isLogin: boolean = false;
    vipPrice;

    @ViewChild('commodityDetail') commodityDetail: CommodityDetailComponent;

    constructor(public commodityProvider: CommodityProvider, public navCtrl: NavController, public route: ActivatedRoute, public platform: Platform,
                public nativeProvider: NativeProvider, public modalCtrl: ModalController, public events: Events, public wechatProvider: WechatService,
                public memberProvider: MemberProvider, public cartProvider: CartService, public appConfig: AppConfig,
                public wechatforAppService: WechatforAppService) {
    }

    ngOnInit() {
        this.buyType = this.route.params["value"].buyType;
        this.isLogin = MemberProvider.isLogin();

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
            this.commodity = data.data;
            this.commodityDetail.getCommentData(this.commodity);
            this.getAttrGroups();
        });

        /*页面跳转的初始化切换，供其他页面进入评价或详情*/
        if (this.route.params["value"].segment != undefined) {
            this.select = this.route.params["value"].segment;
        }

        this.initShareData(false);
        this.getVipPrice();
    }

    ionViewWillLeave() {
        this.modalCtrl.dismiss();
    }

    addCart(writeOrder?: boolean) {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }
        if (!this.selectProduct) {
            this.attrModal(writeOrder);
            return
        }
        if (this.buyType == 'gift') {  //送礼专区使用的，该项目暂时没有这个
            this.addGift();
        } else {
            this.addCommonCart(writeOrder);
        }
    }

    addCommonCart(writeOrder) {
        this.cartProvider.addShopCart(MemberProvider.getLoginMember().id, this.selectProduct.id, this.selectProduct.num).then(e => {
            if (e.result == "SUCCESS") {
                this.nativeProvider.showToastFormI4("添加成功!", () => {
                    if (writeOrder) {
                        this.events.publish(REFRESH_SHOPPINGCART);
                        this.navCtrl.navigateForward("/app/tabs/shoppingCart");
                    }
                })
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        })
    }

    //todo
    addGift() {
        this.navCtrl.navigateForward(["ConfirmGiftPage", {productId: this.selectProduct.id, num: this.selectProduct.num}])
    }

    /*立即购买*/
    goWriteOrder() {
        this.addCart(true);
    }

    goShoppingCart() {
        this.events.publish(REFRESH_SHOPPINGCART);
        this.navCtrl.navigateForward("/app/tabs/shoppingCart");
    }

    async attrModal(writeOrder?: boolean) {
        let data: any = {
            attrGroups: this.attrGroups,
            imgPath: this.commodity.imgPath,
            products: this.commodity.products,
            commodityName: this.commodity.commodityName,
            writeOrder: writeOrder,
            buyType: this.buyType
        };

        const modal = await this.modalCtrl.create({
                component: AttrModalPage,
                componentProps: data
            }
        );
        await modal.present();

        await modal.onDidDismiss().then(data => {
            if (data.data) {
                this.selectProduct = data.data;
            }
        });
    }

    async paramsModal() {
        const modal = await this.modalCtrl.create({
                component: ParamsModalPage,
                componentProps: {attrGroups: this.attrGroups}
            }
        );
        await modal.present();
    }

    async openCouponGrantModal() {
        const modal = await this.modalCtrl.create({
                component: ServiceModalPage,
                componentProps: {couponGrantConfig: this.commodity.couponGrantConfig}
            }
        );
        await modal.present();
    }

    async openShareModal() {
        this.initShareData();
        // if (!this.appConfig.isApp) {
        // const modal = await this.modalCtrl.create({
        //         component: ShareClickModalPage,
        //     }
        // );
        // await modal.present();
        // }
    }

    getAttrGroups() {
        this.commodity.products.forEach(e3 => {
            e3.attributes.forEach(e4 => {
                let filter = this.attrGroups.filter(e5 => e5.attrName == e4.attrName);
                if (filter.length > 0) {
                    let filter1 = filter[0].attrValues.filter(e6 => e6.attrValue == e4.attrValue);
                    if (filter1.length == 0) {
                        filter[0].attrValues.push({id: e4.id, attrValue: e4.attrValue})
                    }
                } else {
                    let attrValues = [];
                    attrValues.push({attrValue: e4.attrValue, id: e4.id});
                    this.attrGroups.push({attrName: e4.attrName, attrValues: attrValues})
                }
            })
        });

        this.attrGroups.sort((e1, e2) => e1.attrName > e2.attrName ? 1 : -1);
        this.attrGroups.forEach(e1 => {
            e1.attrValues.sort((e1, e2) => e1.id > e2.id ? 1 : -1);
        })
    }

    onCustom($event) {
        this.select = $event;
    }

    initShareData(isShowModal = true) {
        let memberId;
        if (MemberProvider.isLogin()) {
            memberId = MemberProvider.getLoginMember().id
        }
        let shareData = {
            title: this.commodity.commodityName,
            desc: this.commodity.commodityShortName,
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?CommodityPage&id=' + this.commodity.id + '&preMemberId=' + memberId + '?'),
            imgUrl: this.commodity.imgPath,
        };

        /**
         * 平台
         */
        this.wechatforAppService.shareDate(shareData, isShowModal)
    };

    getVipPrice() {
        if (!this.isLogin) {
            return;
        }
        try {
            let vipPrice: number[] = [];
            this.commodity.products.forEach(e1 => {
                if (e1.levelPrices) {
                    e1.levelPrices.forEach(e2 => {
                        vipPrice.push(e2.levelPrice);
                    })
                }
            });
            if (vipPrice.length > 0) {
                this.vipPrice = Math.min.apply(null, vipPrice);
            }
        } catch (e) {
            console.log(e);
        }
    }

    /**
     * 二维码海报
     */
    async openSharePoster() {
        let memberId;
        if (MemberProvider.isLogin()) {
            memberId = MemberProvider.getLoginMember().id
        }
        let url = this.appConfig.shareURL + 'wechatShare.html?CommodityPage&id=' + this.commodity.id + '&preMemberId=' + memberId + '?';
        const modal = await this.modalCtrl.create({
                component: ShareModalPage,
                componentProps: {url: url, commodityId: this.commodity.id}
            }
        );
        await modal.present();
    }


}
