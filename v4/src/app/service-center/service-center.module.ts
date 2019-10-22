import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {ServiceCenterPage} from './service-center.page';
import {ComponentsModule} from "../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: ServiceCenterPage
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
    declarations: [ServiceCenterPage]
})
export class ServiceCenterPageModule {
}
