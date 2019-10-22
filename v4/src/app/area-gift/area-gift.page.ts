import {Component, OnInit} from '@angular/core';
import {BannerProvider} from "../../services/banner-service/banner";
import {NativeProvider} from "../../services/native-service/native";
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-area-gift',
    templateUrl: './area-gift.page.html',
    styleUrls: ['./area-gift.page.scss'],
})
export class AreaGiftPage implements OnInit {

    data;

    constructor(public bannerProvider: BannerProvider, public nativeProvider: NativeProvider, public navCtrl: NavController) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.bannerProvider.getAdvertisingCommodity(7).then(data => {
            if (data.result == "SUCCESS") {
                this.data = data.data[0];
            } else {
                this.nativeProvider.showToastFormI4(data.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    goCommodity(commodityId) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodityId, buyType:"gift"}])
    }

    goConfirmGift() {
        this.navCtrl.navigateForward("ConfirmGiftPage")
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

}
