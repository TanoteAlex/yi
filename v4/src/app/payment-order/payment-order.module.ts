import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {PaymentOrderPage} from './payment-order.page';
import {ComponentsModule} from "../components/components.module";
import {PaymentOrderResolverService} from "./payment-order-resolver.service";

const routes: Routes = [
    {
        path: '',
        component: PaymentOrderPage,
        resolve:{data:PaymentOrderResolverService}
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
    declarations: [PaymentOrderPage]
})
export class PaymentOrderPageModule {
}
