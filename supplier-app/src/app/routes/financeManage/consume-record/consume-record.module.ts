import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {ConsumeRecordRoutingModule} from './consume-record-routing.module';
import {ListConsumeRecordComponent} from './list-consume-record/list-consume-record.component';
import {AddConsumeRecordComponent} from './add-consume-record/add-consume-record.component';
import {EditConsumeRecordComponent} from './edit-consume-record/edit-consume-record.component';
import {FormConsumeRecordComponent} from './form-consume-record/form-consume-record.component';
import {ViewConsumeRecordComponent} from './view-consume-record/view-consume-record.component';
import {ComponentsModule} from "../../components/components.module";
import {ConsumeRecordService} from '../../services/consume-record.service';

const COMPONENTS = [
  ListConsumeRecordComponent,
  AddConsumeRecordComponent,
  EditConsumeRecordComponent,
  FormConsumeRecordComponent,
  ViewConsumeRecordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
ConsumeRecordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[ConsumeRecordService]
})
export class ConsumeRecordModule { }
