import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {CommListPage} from './comm-list.page';
import {PipesModule} from "../../pipes/pipes.module";
import {ScreenPage} from "../screen/screen.page";

const routes: Routes = [
    {
        path: '',
        component: CommListPage
    }
];

@NgModule({
    entryComponents: [ScreenPage],
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        PipesModule,
        RouterModule.forChild(routes)
    ],
    declarations: [CommListPage, ScreenPage],
})
export class CommListPageModule {
}
