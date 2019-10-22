

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListRegionGroupComponent} from './list-region-group/list-region-group.component';
import {AddRegionGroupComponent} from './add-region-group/add-region-group.component';
import {EditRegionGroupComponent} from './edit-region-group/edit-region-group.component';
import {ViewRegionGroupComponent} from './view-region-group/view-region-group.component';
import {RegionGroupService} from '../../services/region-group.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRegionGroupComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRegionGroupComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRegionGroupComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRegionGroupComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RegionGroupService],
})
export class RegionGroupRoutingModule {
}