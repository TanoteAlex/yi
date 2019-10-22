import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {ShowThreeCommoditiesComponent} from './show-three-commodities/show-three-commodities.component';
import {ShowFourCommoditiesComponent} from './show-four-commodities/show-four-commodities.component';
import {ShowTwoCommoditiesComponent} from "./show-two-commodities/show-two-commodities.component";
import {ImgLazyLoadModule} from "../img-lazy-load/img-lazy-load.component.module";
import {PipesModule} from "../../../pipes/pipes.module";
import {ShowCommoditiesComponent} from './show-commodities/show-commodities.component';
import {CommodityListFirstModule} from "../commodity-list-first/commodity-list-first.component.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        ImgLazyLoadModule,
        PipesModule,
        CommodityListFirstModule,
    ],
    entryComponents: [
        ShowTwoCommoditiesComponent,
        ShowThreeCommoditiesComponent,
        ShowFourCommoditiesComponent,
    ],
    declarations: [
        ShowTwoCommoditiesComponent,
        ShowThreeCommoditiesComponent,
        ShowFourCommoditiesComponent,
        ShowCommoditiesComponent
    ],
    exports: [
        ShowCommoditiesComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
/**
 * 商品展示模式
 */
export class ShowCommoditiesComponentModule {
}
