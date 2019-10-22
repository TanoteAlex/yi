


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { IntegralCashRoutingModule } from './integral-cash-routing.module';
import { ListIntegralCashComponent } from './list-integral-cash/list-integral-cash.component';
import { AddIntegralCashComponent } from './add-integral-cash/add-integral-cash.component';
import { EditIntegralCashComponent } from './edit-integral-cash/edit-integral-cash.component';
import { FormIntegralCashComponent } from './form-integral-cash/form-integral-cash.component';
import { ViewIntegralCashComponent } from './view-integral-cash/view-integral-cash.component';
import { ComponentsModule } from "../../components/components.module";
import { IntegralCashService } from '../../services/integral-cash.service';

const COMPONENTS = [
  ListIntegralCashComponent,
  AddIntegralCashComponent,
  EditIntegralCashComponent,
  FormIntegralCashComponent,
  ViewIntegralCashComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
IntegralCashRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[IntegralCashService]
})
export class IntegralCashModule { }