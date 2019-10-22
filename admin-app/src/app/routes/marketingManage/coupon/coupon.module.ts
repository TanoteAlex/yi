import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {CouponRoutingModule} from './coupon-routing.module';
import {ListCouponComponent} from './list-coupon/list-coupon.component';
import {AddCouponComponent} from './add-coupon/add-coupon.component';
import {EditCouponComponent} from './edit-coupon/edit-coupon.component';
import {FormCouponComponent} from './form-coupon/form-coupon.component';
import {ViewCouponComponent} from './view-coupon/view-coupon.component';
import {ComponentsModule} from "../../components/components.module";
import {CouponService} from '../../services/coupon.service';
import {CommodityService} from "../../services/commodity.service";
import {MemberLevelService} from "../../services/member-level.service";
import {CouponReceiveService} from "../../services/coupon-receive.service";

const COMPONENTS = [
  ListCouponComponent,
  AddCouponComponent,
  EditCouponComponent,
  FormCouponComponent,
  ViewCouponComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    CouponRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers: [CouponService, CommodityService, MemberLevelService, CouponReceiveService, CouponReceiveService]
})
export class CouponModule {
}
