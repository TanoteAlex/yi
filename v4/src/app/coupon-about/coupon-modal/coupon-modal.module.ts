import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {CouponModalPage} from './coupon-modal.page';
import {ComponentsModule} from "../../components/components.module";
import {CouponModule} from "../../components/coupon/coupon/coupon.component.module";
import {CouponStorageModule} from "../../components/coupon/coupon-storage/coupon-storage.component.module";

const routes: Routes = [
    {
        path: '',
        component: CouponModalPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        CouponModule,
        CouponStorageModule,
        RouterModule.forChild(routes)
    ],
    declarations: [CouponModalPage]
})
export class CouponModalPageModule {
}
