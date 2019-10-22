import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {CouponClickModalPage} from './coupon-click-modal.page';
import {ComponentsModule} from "../../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: CouponClickModalPage
    }
];

@NgModule({
    entryComponents: [CouponClickModalPage],
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [CouponClickModalPage]
})
export class CouponClickModalPageModule {
}
