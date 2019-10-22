import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListGrantCouponComponent} from './list-grant-coupon/list-grant-coupon.component';
import {ViewGrantCouponComponent} from './view-grant-coupon/view-grant-coupon.component';
import {CouponService} from '../../services/coupon.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list',component:ListGrantCouponComponent, data: {breadcrumb: '列表'}},
    {path: 'view/:objId',component:ViewGrantCouponComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponService],
})
export class GrantCouponRoutingModule {
}
