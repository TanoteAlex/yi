

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListRewardComponent} from './list-reward/list-reward.component';
import {AddRewardComponent} from './add-reward/add-reward.component';
import {EditRewardComponent} from './edit-reward/edit-reward.component';
import {ViewRewardComponent} from './view-reward/view-reward.component';
import {RewardService} from '../../services/reward.service';
import {PrizeService} from "../../services/prize.service";


const routes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListRewardComponent, data: { breadcrumb: '列表' } },
    { path: 'add', component: AddRewardComponent, data: { breadcrumb: '新增' } },
    { path: 'edit/:objId', component: EditRewardComponent, data: { breadcrumb: '编辑' } },
    { path: 'view/:objId', component: ViewRewardComponent, data: { breadcrumb: '详情' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [RewardService, PrizeService],
})
export class RewardRoutingModule {
}
