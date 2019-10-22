import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {PaymentFailPage} from './payment-fail.page';
import {ComponentsModule} from "../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: PaymentFailPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [PaymentFailPage]
})
export class PaymentFailPageModule {
}
