


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { AreaColumnCommodityRoutingModule } from './area-column-commodity-routing.module';
import { ListAreaColumnCommodityComponent } from './list-area-column-commodity/list-area-column-commodity.component';
import { AddAreaColumnCommodityComponent } from './add-area-column-commodity/add-area-column-commodity.component';
import { EditAreaColumnCommodityComponent } from './edit-area-column-commodity/edit-area-column-commodity.component';
import { FormAreaColumnCommodityComponent } from './form-area-column-commodity/form-area-column-commodity.component';
import { ViewAreaColumnCommodityComponent } from './view-area-column-commodity/view-area-column-commodity.component';
import { ComponentsModule } from "../../components/components.module";
import { AreaColumnCommodityService } from '../../services/area-column-commodity.service';

const COMPONENTS = [
  ListAreaColumnCommodityComponent,
  AddAreaColumnCommodityComponent,
  EditAreaColumnCommodityComponent,
  FormAreaColumnCommodityComponent,
  ViewAreaColumnCommodityComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
AreaColumnCommodityRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[AreaColumnCommodityService]
})
export class AreaColumnCommodityModule { }