import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {ReturnReasonModalPage} from './return-reason-modal.page';

const routes: Routes = [
    {
        path: '',
        component: ReturnReasonModalPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RouterModule.forChild(routes)
    ],
    declarations: [ReturnReasonModalPage]
})
export class ReturnReasonModalPageModule {
}
