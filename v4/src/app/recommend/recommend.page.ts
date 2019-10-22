import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";
import {BannerProvider} from "../../services/banner-service/banner";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-recommend',
    templateUrl: './recommend.page.html',
    styleUrls: ['./recommend.page.scss'],
})
export class RecommendPage {

    list;

    constructor(public nativeProvider: NativeProvider, public navCtrl: NavController, public bannerProvider: BannerProvider) {
    }

    ionViewWillEnter() {
        this.bannerProvider.getRecommends().then(e => {
            if (e.result == "SUCCESS") {
                this.list = e.data;
                this.transform(this.list);
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    transform(data) {
        if (data) {
            data.forEach(e => {
                e.commodities = e.recommendCommodities.map(e1 => {
                    return {
                        id: e1.commodity.id,
                        imgPath: e1.commodity.imgPath,
                        productName: e1.commodity.commodityName,
                        productShortName: e1.commodity.commodityShortName,
                        discountInfo: e1.commodity.discountInfo,
                        originalPrice: e1.commodity.originalPrice,
                        currentPrice: e1.commodity.currentPrice,
                        levelPrices:e1.commodity.levelPrices,
                        returnVoucher:e1.commodity.returnVoucher,
                        freightSet:e1.commodity.freightSet,
                        unifiedFreight:e1.commodity.unifiedFreight,
                        commodityType:e1.commodity.commodityType,
                    }
                });
            })
        }
    }

    goCommodity(id) {
        this.navCtrl.navigateForward(["CommodityPage", {id: id}])
    }

    private getVip(commodity) {
        return parseInt(commodity.levelPrices[0].levelPrice, 10);
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
