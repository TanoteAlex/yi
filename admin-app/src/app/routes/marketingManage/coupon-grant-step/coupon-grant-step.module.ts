


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { CouponGrantStepRoutingModule } from './coupon-grant-step-routing.module';
import { ListCouponGrantStepComponent } from './list-coupon-grant-step/list-coupon-grant-step.component';
import { AddCouponGrantStepComponent } from './add-coupon-grant-step/add-coupon-grant-step.component';
import { EditCouponGrantStepComponent } from './edit-coupon-grant-step/edit-coupon-grant-step.component';
import { FormCouponGrantStepComponent } from './form-coupon-grant-step/form-coupon-grant-step.component';
import { ViewCouponGrantStepComponent } from './view-coupon-grant-step/view-coupon-grant-step.component';
import { ComponentsModule } from "../../components/components.module";
import { CouponGrantStepService } from '../../services/coupon-grant-step.service';

const COMPONENTS = [
  ListCouponGrantStepComponent,
  AddCouponGrantStepComponent,
  EditCouponGrantStepComponent,
  FormCouponGrantStepComponent,
  ViewCouponGrantStepComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
CouponGrantStepRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CouponGrantStepService]
})
export class CouponGrantStepModule { }