


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { RewardRoutingModule } from './reward-routing.module';
import { ListRewardComponent } from './list-reward/list-reward.component';
import { AddRewardComponent } from './add-reward/add-reward.component';
import { EditRewardComponent } from './edit-reward/edit-reward.component';
import { FormRewardComponent } from './form-reward/form-reward.component';
import { ViewRewardComponent } from './view-reward/view-reward.component';
import { ComponentsModule } from "../../components/components.module";
import { RewardService } from '../../services/reward.service';
import {PrizeService} from "../../services/prize.service";

const COMPONENTS = [
    ListRewardComponent,
    AddRewardComponent,
    EditRewardComponent,
    FormRewardComponent,
    ViewRewardComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        RewardRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [RewardService, PrizeService]
})
export class RewardModule { }
