


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { AccountRecordRoutingModule } from './account-record-routing.module';
import { ListAccountRecordComponent } from './list-account-record/list-account-record.component';
import { AddAccountRecordComponent } from './add-account-record/add-account-record.component';
import { EditAccountRecordComponent } from './edit-account-record/edit-account-record.component';
import { FormAccountRecordComponent } from './form-account-record/form-account-record.component';
import { ViewAccountRecordComponent } from './view-account-record/view-account-record.component';
import { ComponentsModule } from "../../components/components.module";
import { AccountRecordService } from '../../services/account-record.service';

const COMPONENTS = [
  ListAccountRecordComponent,
  AddAccountRecordComponent,
  EditAccountRecordComponent,
  FormAccountRecordComponent,
  ViewAccountRecordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
AccountRecordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[AccountRecordService]
})
export class AccountRecordModule { }