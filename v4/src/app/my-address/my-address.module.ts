import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {PipesModule} from "../../pipes/pipes.module";

import {IonicModule} from '@ionic/angular';

import {MyAddressPage} from './my-address.page';
import {ComponentsModule} from "../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: MyAddressPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        PipesModule,
        ComponentsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [MyAddressPage]
})
export class MyAddressPageModule {
}
