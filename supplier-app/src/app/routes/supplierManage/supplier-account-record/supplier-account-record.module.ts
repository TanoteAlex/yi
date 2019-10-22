


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { SupplierAccountRecordRoutingModule } from './supplier-account-record-routing.module';
import { ListSupplierAccountRecordComponent } from './list-supplier-account-record/list-supplier-account-record.component';
import { AddSupplierAccountRecordComponent } from './add-supplier-account-record/add-supplier-account-record.component';
import { EditSupplierAccountRecordComponent } from './edit-supplier-account-record/edit-supplier-account-record.component';
import { FormSupplierAccountRecordComponent } from './form-supplier-account-record/form-supplier-account-record.component';
import { ViewSupplierAccountRecordComponent } from './view-supplier-account-record/view-supplier-account-record.component';
import { ComponentsModule } from "../../components/components.module";
import { SupplierAccountRecordService } from '../../services/supplier-account-record.service';

const COMPONENTS = [
  ListSupplierAccountRecordComponent,
  AddSupplierAccountRecordComponent,
  EditSupplierAccountRecordComponent,
  FormSupplierAccountRecordComponent,
  ViewSupplierAccountRecordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SupplierAccountRecordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierAccountRecordService]
})
export class SupplierAccountRecordModule { }