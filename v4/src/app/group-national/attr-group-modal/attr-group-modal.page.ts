import {Component, OnInit} from '@angular/core';
import {ModalController, NavController, NavParams} from "@ionic/angular";
import {MemberProvider} from "../../../services/member-service/member";
import {OrderProvider} from "../../../services/order-service/order";
import {NativeProvider} from "../../../services/native-service/native";
import {GroupSharePagePage} from "../group-share-page/group-share-page.page";

@Component({
    selector: 'app-attr-group-modal',
    templateUrl: './attr-group-modal.page.html',
    styleUrls: ['./attr-group-modal.page.scss'],
})
export class AttrGroupModalPage implements OnInit {

    groupInfo;
    /*数量*/
    amount: number = 1;

    /*订单类型，openGroup,joinGroup*/
    orderType;


    constructor(public navParam: NavParams, public modalCtrl: ModalController, public orderProvider: OrderProvider, public nativeProvider: NativeProvider,
                public navCtrl: NavController) {
        this.orderType = this.navParam.data.orderType;
        this.groupInfo = this.navParam.data.data;
    }

    ngOnInit() {
    }

    reduce() {
        if (this.amount > 1) {
            this.amount--;
        }
    }

    add() {
        this.amount++;
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

    /**
     * groupOrderType 0默认订单 1开团，参团
     * groupType 拼团类型（1.全国拼团 2.小区拼团 3.返现拼团 4.秒杀活动）
     * groupId 拼团配置ID
     * nationalGroupRecordId 0普通订单和开团 id参团时为开团
     */
    goShoppingCart() {
        if (!MemberProvider.isLogin()) {
            this.modalCtrl.dismiss();
            this.navCtrl.navigateForward("LoginPage");
            return
        }

        let shoppingCartBo = {
            member: {id: MemberProvider.getLoginMember().id},
            openGroupBuy: {},
            groupOrderType: "",
            groupBuyProduct: {id: this.groupInfo.id},
            quantity: this.amount,
        };

        if (this.orderType == "openGroup") {
            shoppingCartBo.groupOrderType = "1";
        } else {
            shoppingCartBo.groupOrderType = "2";
            shoppingCartBo.openGroupBuy = {id: this.navParam.data.groupId};
        }

        this.modalCtrl.dismiss();
        this.navCtrl.navigateForward(["WriteOrderGroupPage", {data: JSON.stringify(shoppingCartBo), orderType: this.orderType}], false);
    }

    openPage() {
        // this.navCtrl.navigateForward(["GroupSharePagePage",{id:"2911"}])
    }

}
