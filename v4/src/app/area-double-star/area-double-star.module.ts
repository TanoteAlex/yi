import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {AreaDoubleStarPage} from './area-double-star.page';
import {PipesModule} from "../../pipes/pipes.module";
import {ComponentsModule} from "../components/components.module";

const routes: Routes = [
    {
        path: '',
        component: AreaDoubleStarPage
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
    declarations: [AreaDoubleStarPage]
})
export class AreaDoubleStarPageModule {
}
