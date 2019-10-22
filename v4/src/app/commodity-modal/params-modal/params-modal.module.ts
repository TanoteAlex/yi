import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes} from '@angular/router';
import {PipesModule} from "../../../pipes/pipes.module";

import {IonicModule} from '@ionic/angular';

import {ParamsModalPage} from './params-modal.page';

const routes: Routes = [
    {
        path: '',
        component: ParamsModalPage
    }
];

@NgModule({
    entryComponents: [ParamsModalPage],
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        PipesModule,
    ],
    declarations: [ParamsModalPage]
})
export class ParamsModalPageModule {
}
