import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListVoucherComponent} from './list-voucher/list-voucher.component';
import {AddVoucherComponent} from './add-voucher/add-voucher.component';
import {EditVoucherComponent} from './edit-voucher/edit-voucher.component';
import {ViewVoucherComponent} from './view-voucher/view-voucher.component';
import {CouponService} from '../../services/coupon.service';
import {CouponReceiveService} from "../../services/coupon-receive.service";
import {CommodityService} from "../../services/commodity.service";
import {MemberLevelService} from "../../services/member-level.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListVoucherComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddVoucherComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditVoucherComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewVoucherComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponService,CommodityService,MemberLevelService,CouponReceiveService],
})
export class VoucherRoutingModule {
}
