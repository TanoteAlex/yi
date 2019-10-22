

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListSalesAreaCommodityComponent} from './list-sales-area-commodity/list-sales-area-commodity.component';
import {AddSalesAreaCommodityComponent} from './add-sales-area-commodity/add-sales-area-commodity.component';
import {EditSalesAreaCommodityComponent} from './edit-sales-area-commodity/edit-sales-area-commodity.component';
import {ViewSalesAreaCommodityComponent} from './view-sales-area-commodity/view-sales-area-commodity.component';
import {SalesAreaCommodityService} from '../../services/sales-area-commodity.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSalesAreaCommodityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSalesAreaCommodityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit', component: EditSalesAreaCommodityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view', component: ViewSalesAreaCommodityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SalesAreaCommodityService],
})
export class SalesAreaCommodityRoutingModule {
}
