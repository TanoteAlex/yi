


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { CommunityGroupRoutingModule } from './community-group-routing.module';
import { ListCommunityGroupComponent } from './list-community-group/list-community-group.component';
import { AddCommunityGroupComponent } from './add-community-group/add-community-group.component';
import { EditCommunityGroupComponent } from './edit-community-group/edit-community-group.component';
import { FormCommunityGroupComponent } from './form-community-group/form-community-group.component';
import { ViewCommunityGroupComponent } from './view-community-group/view-community-group.component';
import { ComponentsModule } from "../../components/components.module";
import { CommunityGroupService } from '../../services/community-group.service';
import {ProductService} from "../../services/product.service";
import {CouponService} from "../../services/coupon.service";
import {CommunityService} from "../../services/community.service";
import {ListCommunityGroupRecordComponent} from "./list-community-group-record/list-community-group-record.component";
import {CommunityGroupRecordService} from "../../services/community-group-record.service";

const COMPONENTS = [
  ListCommunityGroupComponent,
  ListCommunityGroupRecordComponent,
  AddCommunityGroupComponent,
  EditCommunityGroupComponent,
  FormCommunityGroupComponent,
  ViewCommunityGroupComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
CommunityGroupRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CommunityGroupService,ProductService,CouponService,CommunityService,CommunityGroupRecordService]
})
export class CommunityGroupModule { }
