import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../../components/components.module";

import {IonicModule} from '@ionic/angular';

import {ApplyReturnPage} from './apply-return.page';

const routes: Routes = [
    {
        path: '',
        component: ApplyReturnPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        ReactiveFormsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [ApplyReturnPage]
})
export class ApplyReturnPageModule {
}
