import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {FreightTemplateRoutingModule} from './freight-template-routing.module';
import {ListFreightTemplateComponent} from './list-freight-template/list-freight-template.component';
import {AddFreightTemplateComponent} from './add-freight-template/add-freight-template.component';
import {EditFreightTemplateComponent} from './edit-freight-template/edit-freight-template.component';
import {FormFreightTemplateComponent} from './form-freight-template/form-freight-template.component';
import {ViewFreightTemplateComponent} from './view-freight-template/view-freight-template.component';
import {ComponentsModule} from "../../components/components.module";
import {FreightTemplateService} from '../../services/freight-template.service';
import {DistrictPipe} from "../../pipes/district/district";
import {AreaService} from "../../services/area.service";

const COMPONENTS = [
  ListFreightTemplateComponent,
  AddFreightTemplateComponent,
  EditFreightTemplateComponent,
  FormFreightTemplateComponent,
  ViewFreightTemplateComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
  imports: [
    SharedModule,
    FreightTemplateRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[FreightTemplateService,AreaService]
})
export class FreightTemplateModule { }
