


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { BasicRuleRoutingModule } from './basic-rule-routing.module';
import { ListBasicRuleComponent } from './list-basic-rule/list-basic-rule.component';
import { AddBasicRuleComponent } from './add-basic-rule/add-basic-rule.component';
import { EditBasicRuleComponent } from './edit-basic-rule/edit-basic-rule.component';
import { FormBasicRuleComponent } from './form-basic-rule/form-basic-rule.component';
import { ViewBasicRuleComponent } from './view-basic-rule/view-basic-rule.component';
import { ComponentsModule } from "../../components/components.module";
import { BasicRuleService } from '../../services/basic-rule.service';

const COMPONENTS = [
  ListBasicRuleComponent,
  AddBasicRuleComponent,
  EditBasicRuleComponent,
  FormBasicRuleComponent,
  ViewBasicRuleComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
BasicRuleRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[BasicRuleService]
})
export class BasicRuleModule { }