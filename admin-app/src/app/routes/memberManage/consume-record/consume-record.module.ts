import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {ConsumeRecordRoutingModule} from './consume-record-routing.module';
import {ListConsumeRecordComponent} from './list-consume-record/list-consume-record.component';
import {ComponentsModule} from "../../components/components.module";
import {ConsumeRecordService} from '../../services/consume-record.service';
import {AccountRecordService} from "../../services/account-record.service";

const COMPONENTS = [
  ListConsumeRecordComponent];

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
  providers:[AccountRecordService]
})
export class ConsumeRecordModule { }
