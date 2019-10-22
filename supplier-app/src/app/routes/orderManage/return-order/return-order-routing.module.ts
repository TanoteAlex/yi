import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListReturnOrderComponent} from './list-return-order/list-return-order.component';
import {AddReturnOrderComponent} from './add-return-order/add-return-order.component';
import {EditReturnOrderComponent} from './edit-return-order/edit-return-order.component';
import {ViewReturnOrderComponent} from './view-return-order/view-return-order.component';
import {ReturnOrderService} from '../../services/return-order.service';
import {UserService} from "../../services/user.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListReturnOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddReturnOrderComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditReturnOrderComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewReturnOrderComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [ReturnOrderService,UserService],
})
export class ReturnOrderRoutingModule {
}
