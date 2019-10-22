import {Component, OnInit} from '@angular/core';
import {BannerProvider} from "../../services/banner-service/banner";
import {NativeProvider} from "../../services/native-service/native";
import {NavController} from "@ionic/angular";
import {SUCCESS} from "../Constants";

@Component({
    selector: 'app-area-double-star',
    templateUrl: './area-double-star.page.html',
    styleUrls: ['./area-double-star.page.scss'],
})
export class AreaDoubleStarPage implements OnInit {

    data;

    constructor(public bannerProvider: BannerProvider, public nativeProvider: NativeProvider, public navCtrl: NavController) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.bannerProvider.getAdvertisingCommodity(3).then(data => {
            if (data.result == SUCCESS) {
                this.data = data.data[0];
                this.transform(this.data);
            } else {
                this.nativeProvider.showToastFormI4(data.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    goCommodity(commodityId) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodityId}])
    }

    goUrl(banner) {
        // 1商品2文章
        if (banner.linkType == 1) {
            this.navCtrl.navigateForward(["CommodityPage", {id: banner.linkId}])
        } else if (banner.linkType == 2) {
            this.navCtrl.navigateForward(["ArticleDetailPage", {"articleId": banner.linkId}])
        } else {
            return
        }
    }

    transform(data) {
        if (data) {
            data.recommendCommodities = data.recommendCommodities.map(e1 => e1.commodity);
        }
    }
}
