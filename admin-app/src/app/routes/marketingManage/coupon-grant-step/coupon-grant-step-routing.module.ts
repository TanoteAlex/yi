

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListCouponGrantStepComponent} from './list-coupon-grant-step/list-coupon-grant-step.component';
import {AddCouponGrantStepComponent} from './add-coupon-grant-step/add-coupon-grant-step.component';
import {EditCouponGrantStepComponent} from './edit-coupon-grant-step/edit-coupon-grant-step.component';
import {ViewCouponGrantStepComponent} from './view-coupon-grant-step/view-coupon-grant-step.component';
import {CouponGrantStepService} from '../../services/coupon-grant-step.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCouponGrantStepComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCouponGrantStepComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCouponGrantStepComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCouponGrantStepComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponGrantStepService],
})
export class CouponGrantStepRoutingModule {
}