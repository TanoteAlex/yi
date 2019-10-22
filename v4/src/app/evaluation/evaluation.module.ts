import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {EvaluationPage} from './evaluation.page';

const routes: Routes = [
    {
        path: '',
        component: EvaluationPage
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
    declarations: [EvaluationPage]
})
export class EvaluationPageModule {
}
