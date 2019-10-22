import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {OrderSettingRoutingModule} from './order-setting-routing.module';
import {ListOrderSettingComponent} from './list-order-setting/list-order-setting.component';
import {AddOrderSettingComponent} from './add-order-setting/add-order-setting.component';
import {EditOrderSettingComponent} from './edit-order-setting/edit-order-setting.component';
import {FormOrderSettingComponent} from './form-order-setting/form-order-setting.component';
import {ViewOrderSettingComponent} from './view-order-setting/view-order-setting.component';
import {ComponentsModule} from "../../components/components.module";
import {OrderSettingService} from '../../services/order-setting.service';

const COMPONENTS = [
  ListOrderSettingComponent,
  AddOrderSettingComponent,
  EditOrderSettingComponent,
  FormOrderSettingComponent,
  ViewOrderSettingComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
OrderSettingRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[OrderSettingService]
})
export class OrderSettingModule { }
