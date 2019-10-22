

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListRebateGroupComponent} from './list-rebate-group/list-rebate-group.component';
import {AddRebateGroupComponent} from './add-rebate-group/add-rebate-group.component';
import {EditRebateGroupComponent} from './edit-rebate-group/edit-rebate-group.component';
import {ViewRebateGroupComponent} from './view-rebate-group/view-rebate-group.component';
import {RebateGroupService} from '../../services/rebate-group.service';
import {ProductService} from "../../services/product.service";
import {CouponService} from "../../services/coupon.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRebateGroupComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRebateGroupComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRebateGroupComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRebateGroupComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RebateGroupService,ProductService,CouponService],
})
export class RebateGroupRoutingModule {
}
