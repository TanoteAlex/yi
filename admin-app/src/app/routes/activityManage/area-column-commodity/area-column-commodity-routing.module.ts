

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListAreaColumnCommodityComponent} from './list-area-column-commodity/list-area-column-commodity.component';
import {AddAreaColumnCommodityComponent} from './add-area-column-commodity/add-area-column-commodity.component';
import {EditAreaColumnCommodityComponent} from './edit-area-column-commodity/edit-area-column-commodity.component';
import {ViewAreaColumnCommodityComponent} from './view-area-column-commodity/view-area-column-commodity.component';
import {AreaColumnCommodityService} from '../../services/area-column-commodity.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAreaColumnCommodityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAreaColumnCommodityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit', component: EditAreaColumnCommodityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view', component: ViewAreaColumnCommodityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AreaColumnCommodityService],
})
export class AreaColumnCommodityRoutingModule {
}
