import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {ReturnOrderRoutingModule} from './return-order-routing.module';
import {ListReturnOrderComponent} from './list-return-order/list-return-order.component';
import {AddReturnOrderComponent} from './add-return-order/add-return-order.component';
import {EditReturnOrderComponent} from './edit-return-order/edit-return-order.component';
import {FormReturnOrderComponent} from './form-return-order/form-return-order.component';
import {ViewReturnOrderComponent} from './view-return-order/view-return-order.component';
import {ComponentsModule} from "../../components/components.module";
import {ReturnOrderService} from '../../services/return-order.service';
import {UserService} from "../../services/user.service";

const COMPONENTS = [
  ListReturnOrderComponent,
  AddReturnOrderComponent,
  EditReturnOrderComponent,
  FormReturnOrderComponent,
  ViewReturnOrderComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
ReturnOrderRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[ReturnOrderService,UserService]
})
export class ReturnOrderModule { }
