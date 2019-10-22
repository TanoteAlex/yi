import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {AttrModalPage} from "./attr-modal";
import {PipesModule} from "../../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: AttrModalPage
    }
];

@NgModule({
    entryComponents: [AttrModalPage],
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        PipesModule,
    ],
    declarations: [AttrModalPage]
})
export class AttrModalPageModule {
}
