import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListBuyCouponsComponent} from './list-buy-coupons/list-buy-coupons.component';
import {AddBuyCouponsComponent} from './add-buy-coupons/add-buy-coupons.component';
import {EditBuyCouponsComponent} from './edit-buy-coupons/edit-buy-coupons.component';
import {ViewBuyCouponsComponent} from './view-buy-coupons/view-buy-coupons.component';
import {CouponService} from '../../services/coupon.service';
import {CouponReceiveService} from "../../services/coupon-receive.service";
import {CommodityService} from "../../services/commodity.service";
import {MemberLevelService} from "../../services/member-level.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListBuyCouponsComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddBuyCouponsComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditBuyCouponsComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewBuyCouponsComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponService,CommodityService,MemberLevelService,CouponReceiveService],
})
export class BuyCouponsRoutingModule{
}
