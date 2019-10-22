

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListGroupBuyOrderComponent} from './list-group-buy-order/list-group-buy-order.component';
import {AddGroupBuyOrderComponent} from './add-group-buy-order/add-group-buy-order.component';
import {EditGroupBuyOrderComponent} from './edit-group-buy-order/edit-group-buy-order.component';
import {ViewGroupBuyOrderComponent} from './view-group-buy-order/view-group-buy-order.component';
import {GroupBuyOrderService} from '../../services/group-buy-order.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListGroupBuyOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddGroupBuyOrderComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditGroupBuyOrderComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewGroupBuyOrderComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [GroupBuyOrderService],
})
export class GroupBuyOrderRoutingModule {
}
