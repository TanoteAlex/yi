


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { LogisticsAddressRoutingModule } from './logistics-address-routing.module';
import { ListLogisticsAddressComponent } from './list-logistics-address/list-logistics-address.component';
import { AddLogisticsAddressComponent } from './add-logistics-address/add-logistics-address.component';
import { EditLogisticsAddressComponent } from './edit-logistics-address/edit-logistics-address.component';
import { FormLogisticsAddressComponent } from './form-logistics-address/form-logistics-address.component';
import { ViewLogisticsAddressComponent } from './view-logistics-address/view-logistics-address.component';
import { ComponentsModule } from "../../components/components.module";
import { LogisticsAddressService } from '../../services/logistics-address.service';

const COMPONENTS = [
  ListLogisticsAddressComponent,
  AddLogisticsAddressComponent,
  EditLogisticsAddressComponent,
  FormLogisticsAddressComponent,
  ViewLogisticsAddressComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
LogisticsAddressRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[LogisticsAddressService]
})
export class LogisticsAddressModule { }