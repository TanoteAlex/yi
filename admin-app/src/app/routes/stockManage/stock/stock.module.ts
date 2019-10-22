


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { StockRoutingModule } from './stock-routing.module';
import { ListStockComponent } from './list-stock/list-stock.component';
import { AddStockComponent } from './add-stock/add-stock.component';
import { EditStockComponent } from './edit-stock/edit-stock.component';
import { FormStockComponent } from './form-stock/form-stock.component';
import { ViewStockComponent } from './view-stock/view-stock.component';
import { ComponentsModule } from "../../components/components.module";
import { StockService } from '../../services/stock.service';

const COMPONENTS = [
  ListStockComponent,
  AddStockComponent,
  EditStockComponent,
  FormStockComponent,
  ViewStockComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
StockRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[StockService]
})
export class StockModule { }