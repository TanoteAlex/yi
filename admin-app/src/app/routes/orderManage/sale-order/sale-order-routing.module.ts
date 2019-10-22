import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListSaleOrderComponent} from './list-sale-order/list-sale-order.component';
import {AddSaleOrderComponent} from './add-sale-order/add-sale-order.component';
import {EditSaleOrderComponent} from './edit-sale-order/edit-sale-order.component';
import {ViewSaleOrderComponent} from './view-sale-order/view-sale-order.component';
import {SaleOrderService} from '../../services/sale-order.service';
import {EditPriceComponent} from "./edit-price/edit-price.component";
import {EditReceivingAddressComponent} from "./edit-receiving-address/edit-receiving-address.component";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSaleOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'list/:objId', component: ListSaleOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSaleOrderComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSaleOrderComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSaleOrderComponent, data: {breadcrumb: '详情'}},
    {path: 'editPrice/:objId', component: EditPriceComponent, data: {breadcrumb: '修改价格'}},
    {path: 'editRecevingAddress/:objId', component: EditReceivingAddressComponent, data: {breadcrumb: '修改收货地址'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SaleOrderService],
})
export class SaleOrderRoutingModule {
}
