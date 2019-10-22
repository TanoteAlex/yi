import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ListAfterSaleOrderComponent} from './list-after-sale-order/list-after-sale-order.component';
import {AddAfterSaleOrderComponent} from './add-after-sale-order/add-after-sale-order.component';
import {EditAfterSaleOrderComponent} from './edit-after-sale-order/edit-after-sale-order.component';
import {ViewAfterSaleOrderComponent} from './view-after-sale-order/view-after-sale-order.component';
import {AfterSaleOrderService} from '../../services/after-sale-order.service';
import {UserService} from "../../services/user.service";
import {AfterSaleProcessService} from "../../services/after-sale-process.service";


const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: 'list', component: ListAfterSaleOrderComponent, data: {breadcrumb: '列表'}},
  {path: 'add', component: AddAfterSaleOrderComponent, data: {breadcrumb: '新增'}},
  {path: 'edit/:objId', component: EditAfterSaleOrderComponent, data: {breadcrumb: '编辑'}},
  {path: 'view/:objId', component: ViewAfterSaleOrderComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AfterSaleOrderService, UserService, AfterSaleProcessService],
})
export class AfterSaleOrderRoutingModule {
}
