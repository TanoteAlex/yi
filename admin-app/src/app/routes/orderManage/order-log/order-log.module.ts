


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { OrderLogRoutingModule } from './order-log-routing.module';
import { ListOrderLogComponent } from './list-order-log/list-order-log.component';
import { AddOrderLogComponent } from './add-order-log/add-order-log.component';
import { EditOrderLogComponent } from './edit-order-log/edit-order-log.component';
import { FormOrderLogComponent } from './form-order-log/form-order-log.component';
import { ViewOrderLogComponent } from './view-order-log/view-order-log.component';
import { ComponentsModule } from "../../components/components.module";
import { OrderLogService } from '../../services/order-log.service';

const COMPONENTS = [
  ListOrderLogComponent,
  AddOrderLogComponent,
  EditOrderLogComponent,
  FormOrderLogComponent,
  ViewOrderLogComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
OrderLogRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[OrderLogService]
})
export class OrderLogModule { }