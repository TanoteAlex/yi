import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListRefundOrderComponent} from './list-refund-order/list-refund-order.component';
import {AddRefundOrderComponent} from './add-refund-order/add-refund-order.component';
import {EditRefundOrderComponent} from './edit-refund-order/edit-refund-order.component';
import {ViewRefundOrderComponent} from './view-refund-order/view-refund-order.component';
import {RefundOrderService} from '../../services/refund-order.service';
import {UserService} from "../../services/user.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRefundOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRefundOrderComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRefundOrderComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRefundOrderComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RefundOrderService,UserService],
})
export class RefundOrderRoutingModule {
}
