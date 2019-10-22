import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {ChangePhonePage} from './change-phone.page';

const routes: Routes = [
    {
        path: '',
        component: ChangePhonePage
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
    declarations: [ChangePhonePage]
})
export class ChangePhonePageModule {
}
