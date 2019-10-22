import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {AfterSaleOrderRoutingModule} from './after-sale-order-routing.module';
import {ListAfterSaleOrderComponent} from './list-after-sale-order/list-after-sale-order.component';
import {AddAfterSaleOrderComponent} from './add-after-sale-order/add-after-sale-order.component';
import {EditAfterSaleOrderComponent} from './edit-after-sale-order/edit-after-sale-order.component';
import {FormAfterSaleOrderComponent} from './form-after-sale-order/form-after-sale-order.component';
import {ViewAfterSaleOrderComponent} from './view-after-sale-order/view-after-sale-order.component';
import {ComponentsModule} from "../../components/components.module";
import {AfterSaleOrderService} from '../../services/after-sale-order.service';
import {UserService} from "../../services/user.service";
import {AfterSaleProcessService} from "../../services/after-sale-process.service";

const COMPONENTS = [
  ListAfterSaleOrderComponent,
  AddAfterSaleOrderComponent,
  EditAfterSaleOrderComponent,
  FormAfterSaleOrderComponent,
  ViewAfterSaleOrderComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    AfterSaleOrderRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers: [AfterSaleOrderService, UserService, AfterSaleProcessService]
})
export class AfterSaleOrderModule {
}
