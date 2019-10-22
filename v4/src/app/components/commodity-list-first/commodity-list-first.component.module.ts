import {NgModule} from '@angular/core';
import {CommodityListFirstComponent} from "./commodity-list-first.component";
import {PipesModule} from "../../../pipes/pipes.module";
import {ImgLazyLoadModule} from "../img-lazy-load/img-lazy-load.component.module";
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {intersectionObserverPreset, LazyLoadImageModule} from 'ng-lazyload-image';

@NgModule({
    declarations: [
        CommodityListFirstComponent
    ],
    imports: [
        IonicModule,
        ImgLazyLoadModule,
        PipesModule,
        CommonModule,
        LazyLoadImageModule.forRoot({
            preset: intersectionObserverPreset
        })
    ],
    exports: [
        CommodityListFirstComponent
    ]
})
export class CommodityListFirstModule {
}
