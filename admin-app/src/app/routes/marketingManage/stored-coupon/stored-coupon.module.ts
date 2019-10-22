import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {StoredCouponRoutingModule} from './stored-coupon-routing.module';
import {ListStoredCouponComponent} from './list-stored-coupon/list-stored-coupon.component';
import {AddStoredCouponComponent} from './add-stored-coupon/add-stored-coupon.component';
import {EditStoredCouponComponent} from './edit-stored-coupon/edit-stored-coupon.component';
import {FormStoredCouponComponent} from './form-stored-coupon/form-stored-coupon.component';
import {ViewStoredCouponComponent} from './view-stored-coupon/view-stored-coupon.component';
import {ComponentsModule} from "../../components/components.module";
import {CouponService} from '../../services/coupon.service';
import {CommodityService} from "../../services/commodity.service";
import {MemberLevelService} from "../../services/member-level.service";
import {CouponReceiveService} from "../../services/coupon-receive.service";

const COMPONENTS = [
  ListStoredCouponComponent,
  AddStoredCouponComponent,
  EditStoredCouponComponent,
  FormStoredCouponComponent,
  ViewStoredCouponComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    StoredCouponRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers: [CouponService, CommodityService, MemberLevelService, CouponReceiveService, CouponReceiveService]
})
export class StoredCouponModule {
}
