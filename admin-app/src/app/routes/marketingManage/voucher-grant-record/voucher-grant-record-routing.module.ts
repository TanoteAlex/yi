

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListVoucherGrantRecordComponent} from './list-voucher-grant-record/list-voucher-grant-record.component';
import {AddVoucherGrantRecordComponent} from './add-voucher-grant-record/add-voucher-grant-record.component';
import {EditVoucherGrantRecordComponent} from './edit-voucher-grant-record/edit-voucher-grant-record.component';
import {ViewVoucherGrantRecordComponent} from './view-voucher-grant-record/view-voucher-grant-record.component';
import {CouponGrantRecordService} from '../../services/coupon-grant-record.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListVoucherGrantRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddVoucherGrantRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditVoucherGrantRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewVoucherGrantRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponGrantRecordService],
})
export class VoucherGrantRecordRoutingModule {
}
