import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {CommodityDetailComponent} from "./commodity-detail.component";
import {StarModule} from "../star/star.component.module";
import {GobackBtnModule} from "../goback-btn/goback-btn.component.module";
import {BannerSlideModule} from "../banner-slide/banner-slide.components.module";
import {ImgLazyLoadModule} from "../img-lazy-load/img-lazy-load.component.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        BannerSlideModule,
        StarModule,
        GobackBtnModule,
        ImgLazyLoadModule,
    ],
    declarations: [
        CommodityDetailComponent
    ],
    exports: [
        CommodityDetailComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommodityDetailModule {
}
