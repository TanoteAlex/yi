import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {PipesModule} from "../../pipes/pipes.module";

import {IonicModule} from '@ionic/angular';

import {FineBalancePage} from './fine-balance.page';
import {ComponentsModule} from "../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: FineBalancePage
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
    declarations: [FineBalancePage]
})
export class FineBalancePageModule {
}
