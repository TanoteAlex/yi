import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListCouponComponent} from './list-coupon/list-coupon.component';
import {AddCouponComponent} from './add-coupon/add-coupon.component';
import {EditCouponComponent} from './edit-coupon/edit-coupon.component';
import {ViewCouponComponent} from './view-coupon/view-coupon.component';
import {CouponService} from '../../services/coupon.service';
import {CouponReceiveService} from "../../services/coupon-receive.service";
import {MemberLevelService} from "../../services/member-level.service";
import {CommodityService} from "../../services/commodity.service";


const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: 'list', component: ListCouponComponent, data: {breadcrumb: '列表'}},
  {path: 'add', component: AddCouponComponent, data: {breadcrumb: '新增'}},
  {path: 'edit/:objId', component: EditCouponComponent, data: {breadcrumb: '编辑'}},
  {path: 'view/:objId', component: ViewCouponComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponService, CommodityService, MemberLevelService, CouponReceiveService, CouponReceiveService],
})
export class CouponRoutingModule {
}
