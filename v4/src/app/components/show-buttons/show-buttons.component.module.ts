import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {ImgLazyLoadModule} from "../img-lazy-load/img-lazy-load.component.module";
import {PipesModule} from "../../../pipes/pipes.module";
import {ShowButtonsMode1Component} from "./show-buttons-mode1/show-buttons-mode1.component";
import {ShowButtonsMode2Component} from "./show-buttons-mode2/show-buttons-mode2.component";
import {ShowButtonsListComponent} from "./show-buttons-list/show-buttons-list.component";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        ImgLazyLoadModule,
        PipesModule,
    ],
    entryComponents: [
        ShowButtonsMode1Component,
        ShowButtonsMode2Component
    ],
    declarations: [
        ShowButtonsMode1Component,
        ShowButtonsMode2Component,
        ShowButtonsListComponent
    ],
    exports: [
        ShowButtonsListComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
/**
 * 商品展示模式
 */
export class ShowButtonsComponentModule {
}
