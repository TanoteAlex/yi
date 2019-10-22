


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { AttributeGroupRoutingModule } from './attribute-group-routing.module';
import { ListAttributeGroupComponent } from './list-attribute-group/list-attribute-group.component';
import { AddAttributeGroupComponent } from './add-attribute-group/add-attribute-group.component';
import { EditAttributeGroupComponent } from './edit-attribute-group/edit-attribute-group.component';
import { FormAttributeGroupComponent } from './form-attribute-group/form-attribute-group.component';
import { ViewAttributeGroupComponent } from './view-attribute-group/view-attribute-group.component';
import { ComponentsModule } from "../../components/components.module";
import { AttributeGroupService } from '../../services/attribute-group.service';
import {CommodityService} from "../../services/commodity.service";

const COMPONENTS = [
  ListAttributeGroupComponent,
  AddAttributeGroupComponent,
  EditAttributeGroupComponent,
  FormAttributeGroupComponent,
  ViewAttributeGroupComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
AttributeGroupRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[AttributeGroupService,CommodityService]
})
export class AttributeGroupModule { }
