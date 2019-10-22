import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {RegionRoutingModule} from './region-routing.module';
import {ListRegionComponent} from './list-region/list-region.component';
import {AddRegionComponent} from './add-region/add-region.component';
import {EditRegionComponent} from './edit-region/edit-region.component';
import {FormRegionComponent} from './form-region/form-region.component';
import {ViewRegionComponent} from './view-region/view-region.component';
import {ComponentsModule} from "../../components/components.module";
import {RegionService} from '../../services/region.service';

const COMPONENTS = [
  ListRegionComponent,
  AddRegionComponent,
  EditRegionComponent,
  FormRegionComponent,
  ViewRegionComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
RegionRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[RegionService]
})
export class RegionModule { }
