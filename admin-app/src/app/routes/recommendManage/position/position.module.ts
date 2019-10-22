


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { PositionRoutingModule } from './position-routing.module';
import { ListPositionComponent } from './list-position/list-position.component';
import { AddPositionComponent } from './add-position/add-position.component';
import { EditPositionComponent } from './edit-position/edit-position.component';
import { FormPositionComponent } from './form-position/form-position.component';
import { ViewPositionComponent } from './view-position/view-position.component';
import { ComponentsModule } from "../../components/components.module";
import { PositionService } from '../../services/position.service';

const COMPONENTS = [
  ListPositionComponent,
  AddPositionComponent,
  EditPositionComponent,
  FormPositionComponent,
  ViewPositionComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
PositionRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[PositionService]
})
export class PositionModule { }