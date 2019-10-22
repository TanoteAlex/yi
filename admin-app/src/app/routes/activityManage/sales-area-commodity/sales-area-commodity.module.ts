


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { SalesAreaCommodityRoutingModule } from './sales-area-commodity-routing.module';
import { ListSalesAreaCommodityComponent } from './list-sales-area-commodity/list-sales-area-commodity.component';
import { AddSalesAreaCommodityComponent } from './add-sales-area-commodity/add-sales-area-commodity.component';
import { EditSalesAreaCommodityComponent } from './edit-sales-area-commodity/edit-sales-area-commodity.component';
import { FormSalesAreaCommodityComponent } from './form-sales-area-commodity/form-sales-area-commodity.component';
import { ViewSalesAreaCommodityComponent } from './view-sales-area-commodity/view-sales-area-commodity.component';
import { ComponentsModule } from "../../components/components.module";
import { SalesAreaCommodityService } from '../../services/sales-area-commodity.service';

const COMPONENTS = [
  ListSalesAreaCommodityComponent,
  AddSalesAreaCommodityComponent,
  EditSalesAreaCommodityComponent,
  FormSalesAreaCommodityComponent,
  ViewSalesAreaCommodityComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SalesAreaCommodityRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SalesAreaCommodityService]
})
export class SalesAreaCommodityModule { }