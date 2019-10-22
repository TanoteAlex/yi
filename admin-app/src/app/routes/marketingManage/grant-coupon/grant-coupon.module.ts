import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {GrantCouponRoutingModule} from './grant-coupon-routing.module';
import {ListGrantCouponComponent} from './list-grant-coupon/list-grant-coupon.component';
import {ViewGrantCouponComponent} from './view-grant-coupon/view-grant-coupon.component';
import {ComponentsModule} from "../../components/components.module";
import {CouponService} from '../../services/coupon.service';
import {CommodityService} from "../../services/commodity.service";
import {MemberLevelService} from "../../services/member-level.service";
import {MemberService} from "../../services/member.service";
import {CouponReceiveService} from "../../services/coupon-receive.service";

const COMPONENTS = [
  ListGrantCouponComponent,
  ViewGrantCouponComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
    GrantCouponRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CouponService,CommodityService,MemberLevelService,MemberService,CouponReceiveService]
})
export class GrantCouponModule { }
