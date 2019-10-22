import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {VoucherGrantConfigRoutingModule} from './voucher-grant-config-routing.module';
import {ListVoucherGrantConfigComponent} from './list-voucher-grant-config/list-voucher-grant-config.component';
import {AddVoucherGrantConfigComponent} from './add-voucher-grant-config/add-voucher-grant-config.component';
import {EditVoucherGrantConfigComponent} from './edit-voucher-grant-config/edit-voucher-grant-config.component';
import {FormVoucherGrantConfigComponent} from './form-voucher-grant-config/form-voucher-grant-config.component';
import {ViewVoucherGrantConfigComponent} from './view-voucher-grant-config/view-voucher-grant-config.component';
import {ComponentsModule} from "../../components/components.module";
import {CouponGrantConfigService} from '../../services/coupon-grant-config.service';
import {CouponService} from "../../services/coupon.service";
import {CommodityService} from "../../services/commodity.service";

const COMPONENTS = [
  ListVoucherGrantConfigComponent,
  AddVoucherGrantConfigComponent,
  EditVoucherGrantConfigComponent,
  FormVoucherGrantConfigComponent,
  ViewVoucherGrantConfigComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    VoucherGrantConfigRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers: [CouponGrantConfigService, CouponService,CommodityService]
})
export class VoucherGrantConfigModule {
}
