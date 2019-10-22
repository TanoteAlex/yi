

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListSalesAreaComponent} from './list-sales-area/list-sales-area.component';
import {AddSalesAreaComponent} from './add-sales-area/add-sales-area.component';
import {EditSalesAreaComponent} from './edit-sales-area/edit-sales-area.component';
import {ViewSalesAreaComponent} from './view-sales-area/view-sales-area.component';
import {SalesAreaService} from '../../services/sales-area.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSalesAreaComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSalesAreaComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSalesAreaComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSalesAreaComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SalesAreaService],
})
export class SalesAreaRoutingModule {
}