import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {ConsigneePage} from './consignee.page';
import {PipesModule} from "../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: ConsigneePage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        ReactiveFormsModule,
        PipesModule,
        RouterModule.forChild(routes)
    ],
    declarations: [ConsigneePage]
})
export class ConsigneePageModule {
}
