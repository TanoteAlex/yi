import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {PersonalInfoPage} from './personal-info.page';
import {ComponentsModule} from "../components/components.module";
import {PipesModule} from "../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: PersonalInfoPage
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
    declarations: [PersonalInfoPage]
})
export class PersonalInfoPageModule {
}
