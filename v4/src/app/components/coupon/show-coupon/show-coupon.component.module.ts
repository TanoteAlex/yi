import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ShowCouponComponent} from './show-coupon.component';
import {CouponStorageModule} from "../coupon-storage/coupon-storage.component.module";
import {CouponModule} from "../coupon/coupon.component.module";
import {CouponStorageComponent} from "../coupon-storage/coupon-storage.component";
import {CouponComponent} from "../coupon/coupon.component";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        CouponStorageModule,
        CouponModule,
    ],
    entryComponents:[
        CouponStorageComponent,
        CouponComponent,
    ],
    declarations: [
        ShowCouponComponent
    ],
    exports: [
        ShowCouponComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShowCouponModule {
}
