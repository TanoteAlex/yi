


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { LoginRecordRoutingModule } from './login-record-routing.module';
import { ListLoginRecordComponent } from './list-login-record/list-login-record.component';
import { AddLoginRecordComponent } from './add-login-record/add-login-record.component';
import { EditLoginRecordComponent } from './edit-login-record/edit-login-record.component';
import { FormLoginRecordComponent } from './form-login-record/form-login-record.component';
import { ViewLoginRecordComponent } from './view-login-record/view-login-record.component';
import { ComponentsModule } from "../../components/components.module";
import { LoginRecordService } from '../../services/login-record.service';

const COMPONENTS = [
  ListLoginRecordComponent,
  AddLoginRecordComponent,
  EditLoginRecordComponent,
  FormLoginRecordComponent,
  ViewLoginRecordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
LoginRecordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[LoginRecordService]
})
export class LoginRecordModule { }