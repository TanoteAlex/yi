import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {GroupSharePagePage} from './group-share-page.page';
import {ComponentsModule} from "../../components/components.module";
import {PipesModule} from "../../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: GroupSharePagePage
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
    declarations: [GroupSharePagePage]
})
export class GroupSharePagePageModule {
}
