import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListStoredCouponComponent} from './list-stored-coupon/list-stored-coupon.component';
import {AddStoredCouponComponent} from './add-stored-coupon/add-stored-coupon.component';
import {EditStoredCouponComponent} from './edit-stored-coupon/edit-stored-coupon.component';
import {ViewStoredCouponComponent} from './view-stored-coupon/view-stored-coupon.component';
import {CouponService} from '../../services/coupon.service';
import {CouponReceiveService} from "../../services/coupon-receive.service";
import {MemberLevelService} from "../../services/member-level.service";
import {CommodityService} from "../../services/commodity.service";


const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: 'list', component: ListStoredCouponComponent, data: {breadcrumb: '列表'}},
  {path: 'add', component: AddStoredCouponComponent, data: {breadcrumb: '新增'}},
  {path: 'edit/:objId', component: EditStoredCouponComponent, data: {breadcrumb: '编辑'}},
  {path: 'view/:objId', component: ViewStoredCouponComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponService, CommodityService, MemberLevelService, CouponReceiveService, CouponReceiveService],
})
export class StoredCouponRoutingModule {
}
