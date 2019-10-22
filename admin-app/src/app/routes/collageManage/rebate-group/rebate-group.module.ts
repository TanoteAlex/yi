


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { RebateGroupRoutingModule } from './rebate-group-routing.module';
import { ListRebateGroupComponent } from './list-rebate-group/list-rebate-group.component';
import { AddRebateGroupComponent } from './add-rebate-group/add-rebate-group.component';
import { EditRebateGroupComponent } from './edit-rebate-group/edit-rebate-group.component';
import { FormRebateGroupComponent } from './form-rebate-group/form-rebate-group.component';
import { ViewRebateGroupComponent } from './view-rebate-group/view-rebate-group.component';
import { ComponentsModule } from "../../components/components.module";
import { RebateGroupService } from '../../services/rebate-group.service';
import {ProductService} from "../../services/product.service";
import {CouponService} from "../../services/coupon.service";

const COMPONENTS = [
  ListRebateGroupComponent,
  AddRebateGroupComponent,
  EditRebateGroupComponent,
  FormRebateGroupComponent,
  ViewRebateGroupComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
RebateGroupRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[RebateGroupService,ProductService,CouponService]
})
export class RebateGroupModule { }
