import { Component, OnInit } from '@angular/core';
import {SUCCESS} from "../Constants";
import {ActivatedRoute} from "@angular/router";
import {BannerProvider} from "../../services/banner-service/banner";
import {NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";
import {CouponService} from "../../services/coupon-service/coupon.service";

@Component({
  selector: 'app-commodities-list',
  templateUrl: './commodities-list.page.html',
  styleUrls: ['./commodities-list.page.scss'],
})
export class CommoditiesListPage implements OnInit {

    couponId;

    commoditiesList = [];
    constructor(public bannerProvider: BannerProvider, public nativeProvider: NativeProvider, public navCtrl: NavController, public route: ActivatedRoute,
                public couponProvider: CouponService) {
        this.couponId = this.route.params["value"].couponId;
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.couponProvider.getCoupon(this.couponId).then( e => {
            if (e.result == SUCCESS){
                this.commoditiesList = e.data.commodities;
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        })
    }

    goCommodity(commodityId) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodityId}])
    }

}
