


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { DistributionLevelRoutingModule } from './distribution-level-routing.module';
import { ListDistributionLevelComponent } from './list-distribution-level/list-distribution-level.component';
import { AddDistributionLevelComponent } from './add-distribution-level/add-distribution-level.component';
import { EditDistributionLevelComponent } from './edit-distribution-level/edit-distribution-level.component';
import { FormDistributionLevelComponent } from './form-distribution-level/form-distribution-level.component';
import { ViewDistributionLevelComponent } from './view-distribution-level/view-distribution-level.component';
import { ComponentsModule } from "../../components/components.module";
import { DistributionLevelService } from '../../services/distribution-level.service';

const COMPONENTS = [
  ListDistributionLevelComponent,
  AddDistributionLevelComponent,
  EditDistributionLevelComponent,
  FormDistributionLevelComponent,
  ViewDistributionLevelComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
DistributionLevelRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[DistributionLevelService]
})
export class DistributionLevelModule { }