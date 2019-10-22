import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {PipesModule} from "../../pipes/pipes.module";
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {MyOrderPage} from './my-order.page';

const routes: Routes = [
    {
        path: '',
        component: MyOrderPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        PipesModule,
        IonicModule,
        ComponentsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [MyOrderPage]
})
export class MyOrderPageModule {
}
