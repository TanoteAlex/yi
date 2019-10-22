import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {RefundOrderRoutingModule} from './refund-order-routing.module';
import {ListRefundOrderComponent} from './list-refund-order/list-refund-order.component';
import {AddRefundOrderComponent} from './add-refund-order/add-refund-order.component';
import {EditRefundOrderComponent} from './edit-refund-order/edit-refund-order.component';
import {FormRefundOrderComponent} from './form-refund-order/form-refund-order.component';
import {ViewRefundOrderComponent} from './view-refund-order/view-refund-order.component';
import {ComponentsModule} from "../../components/components.module";
import {RefundOrderService} from '../../services/refund-order.service';
import {UserService} from "../../services/user.service";

const COMPONENTS = [
  ListRefundOrderComponent,
  AddRefundOrderComponent,
  EditRefundOrderComponent,
  FormRefundOrderComponent,
  ViewRefundOrderComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
RefundOrderRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[RefundOrderService,UserService]
})
export class RefundOrderModule { }
