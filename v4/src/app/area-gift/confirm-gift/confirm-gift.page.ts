import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {scrollToZero} from "../../../util/bug-util";

@Component({
    selector: 'app-confirm-gift',
    templateUrl: './confirm-gift.page.html',
    styleUrls: ['./confirm-gift.page.scss'],
})
export class ConfirmGiftPage implements OnInit {

    list;

    constructor(public navCtrl: NavController) {
    }

    ngOnInit() {
        this.list=this.data.data;
    }

    reduce(i) {
        if (this.list[i].quantity == 1) {
            return
        }
        this.list[i].quantity--;
    }

    add(i) {
        this.list[i].quantity++;
    }

    goAreaGift(){
        this.navCtrl.navigateRoot("AreaGiftPage");
    }

    goShareGift(){
        this.navCtrl.navigateForward("ShareGiftPage")
    }

    blur(){
        scrollToZero();
    }

    data = JSON.parse("{\"result\":\"SUCCESS\",\"message\":null,\"data\":[{\"id\":212,\"guid\":\"60acf0380c2941bbacf0380c2901bbdf\",\"member\":{\"id\":70,\"guid\":\"5361e8f3aa72442fa1e8f3aa72642fe1\",\"username\":\"16620906808\",\"password\":\"123456789\",\"nickname\":\"那年夏天宁静的海\",\"memberType\":0,\"vip\":null,\"province\":\"440000\",\"city\":\"440300\",\"district\":\"440301\",\"address\":\"恒大城市之光(公寓)\",\"openId\":\"oHRf6v5VdIeFvsoLiYI7FIkAkTRU\",\"appOpenId\":null,\"unionId\":\"oSPpD1JL-Ld2UIfkv74Fys8-RxZ4\",\"createTime\":\"2018-10-15 10:01:30\",\"deleted\":0,\"delTime\":null,\"payPassword\":null,\"avater\":\"http://app.my11mall.com:8381/attachment/show/421e80f7-fc8f-4f34-9e80-f7fc8f3f343d\"},\"product\":{\"id\":1091,\"guid\":\"660122e7a8bb46178122e7a8bb2617bf\",\"productNo\":\"1812051350061457\",\"productName\":\"【5盒装】海底捞清油麻辣香辣素食方便速食即食懒人自煮小火锅400g 400g*5盒\",\"productShortName\":\"海底捞清油麻辣香辣包邮400g*5盒 400g*5盒\",\"sort\":0,\"volume\":0.00,\"weight\":0.00,\"description\":null,\"originalPrice\":219.00,\"currentPrice\":86.40,\"supplyPrice\":86.40,\"sku\":null,\"stockQuantity\":1000,\"createTime\":\"2018-12-05 13:50:06\",\"deleted\":0,\"delTime\":null,\"costPrice\":0.00,\"vipPrice\":null,\"productImgPath\":\"http://adminserver.my11mall.com/attachment/show/ac934e1a-7b74-46aa-934e-1a7b7496aaca\",\"attributes\":[{\"id\":336,\"guid\":\"a90b8a8a93df4e788b8a8a93df3e78e0\",\"attrName\":\"规格\",\"attrValue\":\"400g*5盒\",\"sort\":0,\"createTime\":\"2018-11-07 22:02:54\",\"remark\":null,\"deleted\":0,\"delTime\":null,\"attributeGroupId\":181,\"imgPath\":null}]},\"quantity\":2,\"price\":0.00,\"discount\":0.00,\"discountInfo\":null,\"state\":0,\"createTime\":\"2018-12-07 10:52:42\"},{\"id\":211,\"guid\":\"74eeb563f3eb491aaeb563f3eb791a7d\",\"member\":{\"id\":70,\"guid\":\"5361e8f3aa72442fa1e8f3aa72642fe1\",\"username\":\"16620906808\",\"password\":\"123456789\",\"nickname\":\"那年夏天宁静的海\",\"memberType\":0,\"vip\":null,\"province\":\"440000\",\"city\":\"440300\",\"district\":\"440301\",\"address\":\"恒大城市之光(公寓)\",\"openId\":\"oHRf6v5VdIeFvsoLiYI7FIkAkTRU\",\"appOpenId\":null,\"unionId\":\"oSPpD1JL-Ld2UIfkv74Fys8-RxZ4\",\"createTime\":\"2018-10-15 10:01:30\",\"deleted\":0,\"delTime\":null,\"payPassword\":null,\"avater\":\"http://app.my11mall.com:8381/attachment/show/421e80f7-fc8f-4f34-9e80-f7fc8f3f343d\"},\"product\":{\"id\":1090,\"guid\":\"0db2996278cf4a74b2996278cf2a7409\",\"productNo\":\"1812051349155674\",\"productName\":\"【12瓶装】海天鲜味生抽500ml 非转基因黄豆酿造酱油 炒菜凉拌火锅调料 500ml*12瓶\",\"productShortName\":\"包邮海天鲜味生抽500ml*12瓶 500ml*12瓶\",\"sort\":0,\"volume\":0.00,\"weight\":0.00,\"description\":null,\"originalPrice\":159.00,\"currentPrice\":71.88,\"supplyPrice\":71.88,\"sku\":null,\"stockQuantity\":1000,\"createTime\":\"2018-12-05 13:49:16\",\"deleted\":0,\"delTime\":null,\"costPrice\":0.00,\"vipPrice\":null,\"productImgPath\":\"http://adminserver.my11mall.com/attachment/show/8f584a8f-e8e6-46f2-984a-8fe8e6f6f297\",\"attributes\":[{\"id\":338,\"guid\":\"d1745635ebf3491cb45635ebf3491c61\",\"attrName\":\"规格\",\"attrValue\":\"500ml*12瓶\",\"sort\":0,\"createTime\":\"2018-11-08 09:57:45\",\"remark\":null,\"deleted\":0,\"delTime\":null,\"attributeGroupId\":181,\"imgPath\":null}]},\"quantity\":1,\"price\":0.00,\"discount\":0.00,\"discountInfo\":null,\"state\":0,\"createTime\":\"2018-12-07 10:36:55\"},{\"id\":212,\"guid\":\"60acf0380c2941bbacf0380c2901bbdf\",\"member\":{\"id\":70,\"guid\":\"5361e8f3aa72442fa1e8f3aa72642fe1\",\"username\":\"16620906808\",\"password\":\"123456789\",\"nickname\":\"那年夏天宁静的海\",\"memberType\":0,\"vip\":null,\"province\":\"440000\",\"city\":\"440300\",\"district\":\"440301\",\"address\":\"恒大城市之光(公寓)\",\"openId\":\"oHRf6v5VdIeFvsoLiYI7FIkAkTRU\",\"appOpenId\":null,\"unionId\":\"oSPpD1JL-Ld2UIfkv74Fys8-RxZ4\",\"createTime\":\"2018-10-15 10:01:30\",\"deleted\":0,\"delTime\":null,\"payPassword\":null,\"avater\":\"http://app.my11mall.com:8381/attachment/show/421e80f7-fc8f-4f34-9e80-f7fc8f3f343d\"},\"product\":{\"id\":1091,\"guid\":\"660122e7a8bb46178122e7a8bb2617bf\",\"productNo\":\"1812051350061457\",\"productName\":\"【5盒装】海底捞清油麻辣香辣素食方便速食即食懒人自煮小火锅400g 400g*5盒\",\"productShortName\":\"海底捞清油麻辣香辣包邮400g*5盒 400g*5盒\",\"sort\":0,\"volume\":0.00,\"weight\":0.00,\"description\":null,\"originalPrice\":219.00,\"currentPrice\":86.40,\"supplyPrice\":86.40,\"sku\":null,\"stockQuantity\":1000,\"createTime\":\"2018-12-05 13:50:06\",\"deleted\":0,\"delTime\":null,\"costPrice\":0.00,\"vipPrice\":null,\"productImgPath\":\"http://adminserver.my11mall.com/attachment/show/ac934e1a-7b74-46aa-934e-1a7b7496aaca\",\"attributes\":[{\"id\":336,\"guid\":\"a90b8a8a93df4e788b8a8a93df3e78e0\",\"attrName\":\"规格\",\"attrValue\":\"400g*5盒\",\"sort\":0,\"createTime\":\"2018-11-07 22:02:54\",\"remark\":null,\"deleted\":0,\"delTime\":null,\"attributeGroupId\":181,\"imgPath\":null}]},\"quantity\":2,\"price\":0.00,\"discount\":0.00,\"discountInfo\":null,\"state\":0,\"createTime\":\"2018-12-07 10:52:42\"}]}")
}
