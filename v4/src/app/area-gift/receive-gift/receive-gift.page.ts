import {Component, OnInit} from '@angular/core';
import {MemberProvider} from "../../../services/member-service/member";
import {ShareData} from "../../../domain/original/share-data.model";
import {ShareModalPage} from "../../share-modal/share-modal.page";
import {WechatService} from "../../../services/wechat-service/wechat.service";
import {ModalController, NavController} from "@ionic/angular";
import {ConsigneeModalPage} from "../../consignee-modal/consignee-modal.page";
import {ConfirmConsigneeModalPage} from "../confirm-consignee-modal/confirm-consignee-modal.page";

@Component({
    selector: 'app-receive-gift',
    templateUrl: './receive-gift.page.html',
    styleUrls: ['./receive-gift.page.scss'],
})
export class ReceiveGiftPage implements OnInit {

    list;

    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData: ShareData;

    /**
     * 填写的地址
     */
    consigneeData;

    constructor(public memberProvider: MemberProvider, public modalCtrl: ModalController, public wechatProvider: WechatService, public navCtrl: NavController,) {
    }

    ngOnInit() {
        this.list = this.data.data;

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
        console.log(("ionViewWillEnter"));
    }

    async giftOthers() {
        let memberId;
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        } else {
            memberId = MemberProvider.getLoginMember().id
        }

        let shareData = {
            title: "送礼专区",
            desc: "送礼内容",
            link: encodeURI(window.location.href.split('#')[0] + 'wechatShare.html?ReceiveGiftPage&preMemberId=' + memberId + '?'),
            imgUrl: window.location.href.split('#')[0] + 'assets/app_icon/customer/%E7%89%88%E6%9C%AC%E5%8F%B7logo@2x.png',
        };
        this.wechatProvider.share(shareData);
        const modal = await this.modalCtrl.create({
                component: ShareModalPage,
            }
        );
        await modal.present();
    }

    async goConsignee() {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }

        const modalAddConsignee = await this.modalCtrl.create({
            component: ConsigneeModalPage,
            componentProps:{address:this.consigneeData}
        });
        await modalAddConsignee.present();
        await modalAddConsignee.onDidDismiss().then(data => {
            if (data.data != undefined) {
                this.consigneeData = data.data;
                this.confirmConsignee();
            }
        })
    }

    async confirmConsignee() {
        const modal = await this.modalCtrl.create({
            component: ConfirmConsigneeModalPage,
            componentProps:{consigneeData:this.consigneeData}
        });
        await modal.present();
        await modal.onDidDismiss().then(data => {
            if(data.data && data.data.isConfirm){
                console.log("确认已确认地址,地址信息为");
                console.log(this.consigneeData)
            }
            if(data.data && !data.data.isConfirm){
                this.goConsignee();
            }
        })
    }




    data = JSON.parse("{\"result\":\"SUCCESS\",\"message\":null,\"data\":[{\"id\":212,\"guid\":\"60acf0380c2941bbacf0380c2901bbdf\",\"member\":{\"id\":70,\"guid\":\"5361e8f3aa72442fa1e8f3aa72642fe1\",\"username\":\"16620906808\",\"password\":\"123456789\",\"nickname\":\"那年夏天宁静的海\",\"memberType\":0,\"vip\":null,\"province\":\"440000\",\"city\":\"440300\",\"district\":\"440301\",\"address\":\"恒大城市之光(公寓)\",\"openId\":\"oHRf6v5VdIeFvsoLiYI7FIkAkTRU\",\"appOpenId\":null,\"unionId\":\"oSPpD1JL-Ld2UIfkv74Fys8-RxZ4\",\"createTime\":\"2018-10-15 10:01:30\",\"deleted\":0,\"delTime\":null,\"payPassword\":null,\"avater\":\"http://app.my11mall.com:8381/attachment/show/421e80f7-fc8f-4f34-9e80-f7fc8f3f343d\"},\"product\":{\"id\":1091,\"guid\":\"660122e7a8bb46178122e7a8bb2617bf\",\"productNo\":\"1812051350061457\",\"productName\":\"【5盒装】海底捞清油麻辣香辣素食方便速食即食懒人自煮小火锅400g 400g*5盒\",\"productShortName\":\"海底捞清油麻辣香辣包邮400g*5盒 400g*5盒\",\"sort\":0,\"volume\":0.00,\"weight\":0.00,\"description\":null,\"originalPrice\":219.00,\"currentPrice\":86.40,\"supplyPrice\":86.40,\"sku\":null,\"stockQuantity\":1000,\"createTime\":\"2018-12-05 13:50:06\",\"deleted\":0,\"delTime\":null,\"costPrice\":0.00,\"vipPrice\":null,\"productImgPath\":\"http://adminserver.my11mall.com/attachment/show/ac934e1a-7b74-46aa-934e-1a7b7496aaca\",\"attributes\":[{\"id\":336,\"guid\":\"a90b8a8a93df4e788b8a8a93df3e78e0\",\"attrName\":\"规格\",\"attrValue\":\"400g*5盒\",\"sort\":0,\"createTime\":\"2018-11-07 22:02:54\",\"remark\":null,\"deleted\":0,\"delTime\":null,\"attributeGroupId\":181,\"imgPath\":null}]},\"quantity\":2,\"price\":0.00,\"discount\":0.00,\"discountInfo\":null,\"state\":0,\"createTime\":\"2018-12-07 10:52:42\"}]}")

}
