


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { NationalGroupRoutingModule } from './national-group-routing.module';
import { ListNationalGroupComponent } from './list-national-group/list-national-group.component';
import { AddNationalGroupComponent } from './add-national-group/add-national-group.component';
import { EditNationalGroupComponent } from './edit-national-group/edit-national-group.component';
import { FormNationalGroupComponent } from './form-national-group/form-national-group.component';
import { ViewNationalGroupComponent } from './view-national-group/view-national-group.component';
import { ComponentsModule } from "../../components/components.module";
import { NationalGroupService } from '../../services/national-group.service';
import {ProductService} from "../../services/product.service";
import {CouponService} from "../../services/coupon.service";
import {ListNationalGroupRecordComponent} from "./list-national-group-record/list-national-group-record.component";
import {NationalGroupRecordService} from "../../services/national-group-record.service";

const COMPONENTS = [
  ListNationalGroupComponent,
  ListNationalGroupRecordComponent,
  AddNationalGroupComponent,
  EditNationalGroupComponent,
  FormNationalGroupComponent,
  ViewNationalGroupComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
NationalGroupRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[NationalGroupService,ProductService,CouponService,NationalGroupRecordService]
})
export class NationalGroupModule { }
