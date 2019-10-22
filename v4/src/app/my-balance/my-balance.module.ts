import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';


import {IonicModule} from '@ionic/angular';

import {MyBalancePage} from './my-balance.page';
import {ComponentsModule} from "../components/components.module";
import {PipesModule} from "../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: MyBalancePage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        PipesModule,
        RouterModule.forChild(routes)
    ],
    declarations: [MyBalancePage]
})
export class MyBalancePageModule {
}
