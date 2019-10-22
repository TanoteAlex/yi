import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {CustomerCenterPage} from './customer-center.page';

const routes: Routes = [
    {
        path: '',
        component: CustomerCenterPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RouterModule.forChild(routes)
    ],
    declarations: [CustomerCenterPage]
})
export class CustomerCenterPageModule {
}
