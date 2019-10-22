import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {OrderDetailPage} from './order-detail.page';
import {OrderDetailResolverService} from "./order-detail-resolver.service";

const routes: Routes = [
    {
        path: '',
        component: OrderDetailPage,
        resolve:{data:OrderDetailResolverService}
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
    declarations: [OrderDetailPage]
})
export class OrderDetailPageModule {
}
