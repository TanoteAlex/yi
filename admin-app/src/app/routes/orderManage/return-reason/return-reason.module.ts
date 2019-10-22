import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {ReturnReasonRoutingModule} from './return-reason-routing.module';
import {ListReturnReasonComponent} from './list-return-reason/list-return-reason.component';
import {AddReturnReasonComponent} from './add-return-reason/add-return-reason.component';
import {EditReturnReasonComponent} from './edit-return-reason/edit-return-reason.component';
import {FormReturnReasonComponent} from './form-return-reason/form-return-reason.component';
import {ViewReturnReasonComponent} from './view-return-reason/view-return-reason.component';
import {ComponentsModule} from "../../components/components.module";
import {ReturnReasonService} from '../../services/return-reason.service';

const COMPONENTS = [
  ListReturnReasonComponent,
  AddReturnReasonComponent,
  EditReturnReasonComponent,
  FormReturnReasonComponent,
  ViewReturnReasonComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
ReturnReasonRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[ReturnReasonService]
})
export class ReturnReasonModule { }
