import { Component, OnInit } from '@angular/core';
import {REFRESH_SHOPPINGCART} from "../../Constants";
import {AttrGroupModalPage} from "../../group-national/attr-group-modal/attr-group-modal.page";
import {GroupPurchaseModalPage} from "../../group-national/group-purchase-modal/group-purchase-modal.page";
import {ServiceModalPage} from "../../commodity-modal/service-modal/service-modal.page";
import {Events, ModalController, NavController} from "@ionic/angular";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NativeProvider} from "../../../services/native-service/native";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-commodity-flash',
  templateUrl: './commodity-flash.page.html',
  styleUrls: ['./commodity-flash.page.scss'],
})
export class CommodityFlashPage implements OnInit {

    select: string = "introduce";

    /*拼团信息*/
    groupInfo;

    /*商品信息*/
    commodity;

    preMemberId

    constructor(public nativeProvider: NativeProvider,
                public navCtrl: NavController,
                public modalCtrl: ModalController,
                public route: ActivatedRoute,
                public domSanitizer: DomSanitizer,
                public events: Events,) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.route.data.subscribe((data) => {
            this.groupInfo = data.data;
            this.commodity = this.groupInfo.commodity;

            this.commodity.description = this.commodity.description.replace('<p', '<p style="font-size:0!important" ');
            this.commodity.description = this.domSanitizer.bypassSecurityTrustHtml(this.commodity.description);
        });

        /*页面跳转的初始化切换，供其他页面进入评价或详情*/
        if (this.route.params["value"].segment != undefined) {
            this.select = this.route.params["value"].segment;
        }
    }

    goShoppingCart() {
        this.events.publish(REFRESH_SHOPPINGCART);
        this.navCtrl.navigateForward("/app/tabs/home");
    }

    /*已开团列表*/
    async openPurchaseModal() {
        const modal = await this.modalCtrl.create({
                component: GroupPurchaseModalPage,
                componentProps: this.groupInfo
            }
        );
        await modal.present();
    }

    async paramsModal() {
        // const modal = await this.modalCtrl.create({
        //         component: ParamsModalPage,
        //         componentProps: {attr: this.commodity.attributeGroups}
        //     }
        // );
        // await modal.present();
    }
    async goGroupWriteOrder() {

    }

    openShareModal() {

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

}
