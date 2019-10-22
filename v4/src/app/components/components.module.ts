import {NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {SearchHeadModule} from "./search-head/search-head.module";
import {TicketModule} from "./ticket/ticket.component.module";
import {StarModule} from "./star/star.component.module";
import {FooterTipModule} from "./footer-tip/footer-tip.component.module";
import {CommodityItemModule} from "./commodity-item/commodity-item.component.module";

import {BaseModule} from "./base/base.component.module";
import {GobackBtnModule} from "./goback-btn/goback-btn.component.module";
import {DistrictsModule} from "./districts/districts.component.module";
import {TimeCountModule} from "./time-count/time-count.component.module";
import {AdvCommodityModule} from "./adv-commodity/adv-commodity.component.module";
import {CenterModalModule} from "./center-modal/center-modal.component.module";
import {CommodityActiveModule} from "./commodity-active/commodity-active.component.module";
import {BannerSlideModule} from "./banner-slide/banner-slide.components.module";
import {CommodityDetailModule} from "./commodity-detail/commodity-detail.component.module";
import {ImgLazyLoadModule} from "./img-lazy-load/img-lazy-load.component.module";
import {ShowCommoditiesComponentModule} from "./show-commodities/show-commodities.component.module";
import {ShowButtonsComponentModule} from "./show-buttons/show-buttons.component.module";
import {AffixComponentsModule} from "./affix/affix.components.module";
import {InfiniteModule} from "./page/infinite/infinite.module";
import {RefreshModule} from "./page/refresh/refresh.module";
import {HomeListComponentModule} from "./home-list/home-list.component.module";
import {CommodityListFirstModule} from "./commodity-list-first/commodity-list-first.component.module";
import {ShowCouponModule} from "./coupon/show-coupon/show-coupon.component.module";
import {SmallTitleLineModule} from "./small-title-line/small-title-line.component.module";
import {NewTimeCountModule} from "./time-count/new-time-count/new-time-count.component.module";

@NgModule({
    declarations: [],
    imports: [IonicModule

    ],
    exports: [
        SearchHeadModule,
        TicketModule,
        CommodityItemModule,
        StarModule,
        FooterTipModule,
        BaseModule,
        GobackBtnModule,
        DistrictsModule,
        // CouponModule,
        TimeCountModule,
        AdvCommodityModule,
        CenterModalModule,
        CommodityActiveModule,
        BannerSlideModule,
        CommodityDetailModule,
        ImgLazyLoadModule,
        AffixComponentsModule,
        ShowCommoditiesComponentModule,
        ShowButtonsComponentModule,
        InfiniteModule,
        RefreshModule,
        HomeListComponentModule,
        CommodityListFirstModule,
        ShowCouponModule,
        SmallTitleLineModule,
        NewTimeCountModule,
    ]
})
export class ComponentsModule {
}
