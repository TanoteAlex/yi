


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { FreightTemplateConfigRoutingModule } from './freight-template-config-routing.module';
import { ListFreightTemplateConfigComponent } from './list-freight-template-config/list-freight-template-config.component';
import { AddFreightTemplateConfigComponent } from './add-freight-template-config/add-freight-template-config.component';
import { EditFreightTemplateConfigComponent } from './edit-freight-template-config/edit-freight-template-config.component';
import { FormFreightTemplateConfigComponent } from './form-freight-template-config/form-freight-template-config.component';
import { ViewFreightTemplateConfigComponent } from './view-freight-template-config/view-freight-template-config.component';
import { ComponentsModule } from "../../components/components.module";
import { FreightTemplateConfigService } from '../../services/freight-template-config.service';

const COMPONENTS = [
  ListFreightTemplateConfigComponent,
  AddFreightTemplateConfigComponent,
  EditFreightTemplateConfigComponent,
  FormFreightTemplateConfigComponent,
  ViewFreightTemplateConfigComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
FreightTemplateConfigRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[FreightTemplateConfigService]
})
export class FreightTemplateConfigModule { }