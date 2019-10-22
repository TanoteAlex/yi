import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {CouponReceivePage} from './coupon-receive.page';
import {PipesModule} from "../../../pipes/pipes.module";
import {ComponentsModule} from "../../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: CouponReceivePage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        PipesModule,
        ComponentsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [CouponReceivePage]
})
export class CouponReceivePageModule {
}
