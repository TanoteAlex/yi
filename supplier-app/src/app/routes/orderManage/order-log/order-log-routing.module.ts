

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListOrderLogComponent} from './list-order-log/list-order-log.component';
import {AddOrderLogComponent} from './add-order-log/add-order-log.component';
import {EditOrderLogComponent} from './edit-order-log/edit-order-log.component';
import {ViewOrderLogComponent} from './view-order-log/view-order-log.component';
import {OrderLogService} from '../../services/order-log.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListOrderLogComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddOrderLogComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditOrderLogComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewOrderLogComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [OrderLogService],
})
export class OrderLogRoutingModule {
}