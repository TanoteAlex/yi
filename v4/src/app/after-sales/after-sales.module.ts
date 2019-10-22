import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";


import {PipesModule} from "../../pipes/pipes.module";
import {IonicModule} from '@ionic/angular';

import {AfterSalesPage} from './after-sales.page';

const routes: Routes = [
    {
        path: '',
        component: AfterSalesPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ComponentsModule,
        PipesModule,
        IonicModule,
        RouterModule.forChild(routes)
    ],
    declarations: [AfterSalesPage]
})
export class AfterSalesPageModule {
}
