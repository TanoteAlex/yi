import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {PrizeRoutingModule} from './prize-routing.module';
import {ListPrizeComponent} from './list-prize/list-prize.component';
import {AddPrizeComponent} from './add-prize/add-prize.component';
import {EditPrizeComponent} from './edit-prize/edit-prize.component';
import {FormPrizeComponent} from './form-prize/form-prize.component';
import {ViewPrizeComponent} from './view-prize/view-prize.component';
import {ComponentsModule} from "../../components/components.module";
import {PrizeService} from '../../services/prize.service';
import {CommodityService} from "../../services/commodity.service";
import {CouponService} from "../../services/coupon.service";

const COMPONENTS = [
  ListPrizeComponent,
  AddPrizeComponent,
  EditPrizeComponent,
  FormPrizeComponent,
  ViewPrizeComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    PrizeRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers: [PrizeService, CommodityService, CouponService]
})
export class PrizeModule {
}
