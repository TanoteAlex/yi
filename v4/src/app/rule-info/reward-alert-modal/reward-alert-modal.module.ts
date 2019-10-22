import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {RewardAlertModalPage} from './reward-alert-modal.page';
import {ComponentsModule} from "../../components/components.module";
import {PipesModule} from "../../../pipes/pipes.module";

const routes: Routes = [
    {
        path: '',
        component: RewardAlertModalPage
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
    declarations: [RewardAlertModalPage]
})
export class RewardAlertModalPageModule {
}
