


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { SalesAreaRoutingModule } from './sales-area-routing.module';
import { ListSalesAreaComponent } from './list-sales-area/list-sales-area.component';
import { AddSalesAreaComponent } from './add-sales-area/add-sales-area.component';
import { EditSalesAreaComponent } from './edit-sales-area/edit-sales-area.component';
import { FormSalesAreaComponent } from './form-sales-area/form-sales-area.component';
import { ViewSalesAreaComponent } from './view-sales-area/view-sales-area.component';
import { ComponentsModule } from "../../components/components.module";
import { SalesAreaService } from '../../services/sales-area.service';

const COMPONENTS = [
  ListSalesAreaComponent,
  AddSalesAreaComponent,
  EditSalesAreaComponent,
  FormSalesAreaComponent,
  ViewSalesAreaComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SalesAreaRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SalesAreaService]
})
export class SalesAreaModule { }