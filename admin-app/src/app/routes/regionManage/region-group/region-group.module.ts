


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { RegionGroupRoutingModule } from './region-group-routing.module';
import { ListRegionGroupComponent } from './list-region-group/list-region-group.component';
import { AddRegionGroupComponent } from './add-region-group/add-region-group.component';
import { EditRegionGroupComponent } from './edit-region-group/edit-region-group.component';
import { FormRegionGroupComponent } from './form-region-group/form-region-group.component';
import { ViewRegionGroupComponent } from './view-region-group/view-region-group.component';
import { ComponentsModule } from "../../components/components.module";
import { RegionGroupService } from '../../services/region-group.service';

const COMPONENTS = [
  ListRegionGroupComponent,
  AddRegionGroupComponent,
  EditRegionGroupComponent,
  FormRegionGroupComponent,
  ViewRegionGroupComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
RegionGroupRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[RegionGroupService]
})
export class RegionGroupModule { }