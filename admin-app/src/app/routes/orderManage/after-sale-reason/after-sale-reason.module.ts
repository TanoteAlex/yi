


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { AfterSaleReasonRoutingModule } from './after-sale-reason-routing.module';
import { ListAfterSaleReasonComponent } from './list-after-sale-reason/list-after-sale-reason.component';
import { AddAfterSaleReasonComponent } from './add-after-sale-reason/add-after-sale-reason.component';
import { EditAfterSaleReasonComponent } from './edit-after-sale-reason/edit-after-sale-reason.component';
import { FormAfterSaleReasonComponent } from './form-after-sale-reason/form-after-sale-reason.component';
import { ViewAfterSaleReasonComponent } from './view-after-sale-reason/view-after-sale-reason.component';
import { ComponentsModule } from "../../components/components.module";
import { AfterSaleReasonService } from '../../services/after-sale-reason.service';

const COMPONENTS = [
  ListAfterSaleReasonComponent,
  AddAfterSaleReasonComponent,
  EditAfterSaleReasonComponent,
  FormAfterSaleReasonComponent,
  ViewAfterSaleReasonComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
AfterSaleReasonRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[AfterSaleReasonService]
})
export class AfterSaleReasonModule { }