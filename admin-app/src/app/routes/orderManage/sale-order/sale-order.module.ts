import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {SaleOrderRoutingModule} from './sale-order-routing.module';
import {ListSaleOrderComponent} from './list-sale-order/list-sale-order.component';
import {AddSaleOrderComponent} from './add-sale-order/add-sale-order.component';
import {EditSaleOrderComponent} from './edit-sale-order/edit-sale-order.component';
import {FormSaleOrderComponent} from './form-sale-order/form-sale-order.component';
import {ViewSaleOrderComponent} from './view-sale-order/view-sale-order.component';
import {ComponentsModule} from "../../components/components.module";
import {SaleOrderService} from '../../services/sale-order.service';
import {EditPriceComponent} from "./edit-price/edit-price.component";
import {EditReceivingAddressComponent} from "./edit-receiving-address/edit-receiving-address.component";
import {ExpressService} from "../../services/express.service";

const COMPONENTS = [
  ListSaleOrderComponent,
  AddSaleOrderComponent,
  EditSaleOrderComponent,
  FormSaleOrderComponent,
  ViewSaleOrderComponent,
  EditPriceComponent,
  EditReceivingAddressComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SaleOrderRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SaleOrderService,ExpressService]
})
export class SaleOrderModule { }
