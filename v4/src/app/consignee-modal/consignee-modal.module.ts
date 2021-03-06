import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {ConsigneeModalPage} from './consignee-modal.page';
import {ComponentsModule} from "../components/components.module";
import {PipesModule} from "../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: ConsigneeModalPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        PipesModule,
        ReactiveFormsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [ConsigneeModalPage]
})
export class ConsigneeModalPageModule {
}
