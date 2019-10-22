import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {ServiceModalPage} from './service-modal.page';

const routes: Routes = [
    {
        path: '',
        component: ServiceModalPage
    }
];

@NgModule({
    entryComponents: [ServiceModalPage],
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        // RouterModule.forChild(routes)
    ],
    declarations: [ServiceModalPage]
})
export class ServiceModalPageModule {
}
