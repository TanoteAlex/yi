


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { BasicInfoRoutingModule } from './basic-info-routing.module';
import { ListBasicInfoComponent } from './list-basic-info/list-basic-info.component';
import { AddBasicInfoComponent } from './add-basic-info/add-basic-info.component';
import { EditBasicInfoComponent } from './edit-basic-info/edit-basic-info.component';
import { FormBasicInfoComponent } from './form-basic-info/form-basic-info.component';
import { ViewBasicInfoComponent } from './view-basic-info/view-basic-info.component';
import { ComponentsModule } from "../../components/components.module";
import { BasicInfoService } from '../../services/basic-info.service';

const COMPONENTS = [
  ListBasicInfoComponent,
  AddBasicInfoComponent,
  EditBasicInfoComponent,
  FormBasicInfoComponent,
  ViewBasicInfoComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
BasicInfoRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[BasicInfoService]
})
export class BasicInfoModule { }