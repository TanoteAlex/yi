import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {RecommendPage} from './recommend.page';
import {PipesModule} from "../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: RecommendPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        PipesModule,
        RouterModule.forChild(routes)
    ],
    declarations: [RecommendPage]
})
export class RecommendPageModule {
}
