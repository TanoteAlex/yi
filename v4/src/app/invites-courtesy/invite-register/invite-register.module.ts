import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {InviteRegisterPage} from './invite-register.page';
import {ComponentsModule} from '../../components/components.module';

const routes: Routes = [
    {
        path: '',
        component: InviteRegisterPage
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
    declarations: [InviteRegisterPage]
})
export class InviteRegisterPageModule {
}
