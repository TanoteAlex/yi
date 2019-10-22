import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {AreaGiftPage} from './area-gift.page';
import {PipesModule} from "../../pipes/pipes.module";
import {ComponentsModule} from "../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: AreaGiftPage
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
    declarations: [AreaGiftPage]
})
export class AreaGiftPageModule {
}
