import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ListVoucherGrantConfigComponent} from './list-voucher-grant-config/list-voucher-grant-config.component';
import {AddVoucherGrantConfigComponent} from './add-voucher-grant-config/add-voucher-grant-config.component';
import {EditVoucherGrantConfigComponent} from './edit-voucher-grant-config/edit-voucher-grant-config.component';
import {ViewVoucherGrantConfigComponent} from './view-voucher-grant-config/view-voucher-grant-config.component';
import {CouponGrantConfigService} from '../../services/coupon-grant-config.service';
import {CouponService} from "../../services/coupon.service";
import {CommodityService} from "../../services/commodity.service";


const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: 'list', component: ListVoucherGrantConfigComponent, data: {breadcrumb: '列表'}},
  {path: 'add', component: AddVoucherGrantConfigComponent, data: {breadcrumb: '新增'}},
  {path: 'edit/:objId', component: EditVoucherGrantConfigComponent, data: {breadcrumb: '编辑'}},
  {path: 'view/:objId', component: ViewVoucherGrantConfigComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponGrantConfigService, CouponService,CommodityService],
})
export class VoucherGrantConfigRoutingModule {
}
