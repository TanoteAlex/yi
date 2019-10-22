


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { IntegralRecordRoutingModule } from './integral-record-routing.module';
import { ListIntegralRecordComponent } from './list-integral-record/list-integral-record.component';
import { AddIntegralRecordComponent } from './add-integral-record/add-integral-record.component';
import { EditIntegralRecordComponent } from './edit-integral-record/edit-integral-record.component';
import { FormIntegralRecordComponent } from './form-integral-record/form-integral-record.component';
import { ViewIntegralRecordComponent } from './view-integral-record/view-integral-record.component';
import { ComponentsModule } from "../../components/components.module";
import { IntegralRecordService } from '../../services/integral-record.service';

const COMPONENTS = [
  ListIntegralRecordComponent,
  AddIntegralRecordComponent,
  EditIntegralRecordComponent,
  FormIntegralRecordComponent,
  ViewIntegralRecordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
IntegralRecordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[IntegralRecordService]
})
export class IntegralRecordModule { }