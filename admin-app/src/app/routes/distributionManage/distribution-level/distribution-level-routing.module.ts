

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListDistributionLevelComponent} from './list-distribution-level/list-distribution-level.component';
import {AddDistributionLevelComponent} from './add-distribution-level/add-distribution-level.component';
import {EditDistributionLevelComponent} from './edit-distribution-level/edit-distribution-level.component';
import {ViewDistributionLevelComponent} from './view-distribution-level/view-distribution-level.component';
import {DistributionLevelService} from '../../services/distribution-level.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListDistributionLevelComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddDistributionLevelComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditDistributionLevelComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewDistributionLevelComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [DistributionLevelService],
})
export class DistributionLevelRoutingModule {
}