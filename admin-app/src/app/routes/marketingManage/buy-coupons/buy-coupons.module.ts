import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {BuyCouponsRoutingModule} from './buy-coupons-routing.module';
import {ListBuyCouponsComponent} from './list-buy-coupons/list-buy-coupons.component';
import {AddBuyCouponsComponent} from './add-buy-coupons/add-buy-coupons.component';
import {EditBuyCouponsComponent} from './edit-buy-coupons/edit-buy-coupons.component';
import {FormBuyCouponsComponent} from './form-buy-coupons/form-buy-coupons.component';
import {ViewBuyCouponsComponent} from './view-buy-coupons/view-buy-coupons.component';
import {ComponentsModule} from "../../components/components.module";
import {CouponService} from '../../services/coupon.service';
import {CommodityService} from "../../services/commodity.service";
import {MemberLevelService} from "../../services/member-level.service";
import {CouponReceiveService} from "../../services/coupon-receive.service";

const COMPONENTS = [
  ListBuyCouponsComponent,
  AddBuyCouponsComponent,
  EditBuyCouponsComponent,
  FormBuyCouponsComponent,
  ViewBuyCouponsComponent
];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
BuyCouponsRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CouponService,CommodityService,MemberLevelService,CouponReceiveService]
})
export class BuyCouponsModule{ }
