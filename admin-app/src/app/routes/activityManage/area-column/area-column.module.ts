


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { AreaColumnRoutingModule } from './area-column-routing.module';
import { ListAreaColumnComponent } from './list-area-column/list-area-column.component';
import { AddAreaColumnComponent } from './add-area-column/add-area-column.component';
import { EditAreaColumnComponent } from './edit-area-column/edit-area-column.component';
import { FormAreaColumnComponent } from './form-area-column/form-area-column.component';
import { ViewAreaColumnComponent } from './view-area-column/view-area-column.component';
import { ComponentsModule } from "../../components/components.module";
import { AreaColumnService } from '../../services/area-column.service';

const COMPONENTS = [
  ListAreaColumnComponent,
  AddAreaColumnComponent,
  EditAreaColumnComponent,
  FormAreaColumnComponent,
  ViewAreaColumnComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
AreaColumnRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[AreaColumnService]
})
export class AreaColumnModule { }