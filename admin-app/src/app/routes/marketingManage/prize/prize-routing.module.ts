

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListPrizeComponent} from './list-prize/list-prize.component';
import {AddPrizeComponent} from './add-prize/add-prize.component';
import {EditPrizeComponent} from './edit-prize/edit-prize.component';
import {ViewPrizeComponent} from './view-prize/view-prize.component';
import {PrizeService} from '../../services/prize.service';
import {CouponService} from "../../services/coupon.service";
import {CommodityService} from "../../services/commodity.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListPrizeComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddPrizeComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditPrizeComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewPrizeComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [PrizeService, CommodityService, CouponService],
})
export class PrizeRoutingModule {
}
