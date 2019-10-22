import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {PipesModule} from "../../../pipes/pipes.module";
import {BannerSlideComponent} from "./banner-slide.component";
import {ImgLazyLoadModule} from "../img-lazy-load/img-lazy-load.component.module";
import {intersectionObserverPreset, LazyLoadImageModule} from 'ng-lazyload-image';

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        PipesModule,
        // ImgLazyLoadModule,
        LazyLoadImageModule.forRoot({
            preset: intersectionObserverPreset
        })
    ],
    declarations: [
        BannerSlideComponent
    ],
    exports: [
        BannerSlideComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BannerSlideModule {
}
