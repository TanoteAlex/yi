


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { VoucherGrantRecordRoutingModule } from './voucher-grant-record-routing.module';
import { ListVoucherGrantRecordComponent } from './list-voucher-grant-record/list-voucher-grant-record.component';
import { AddVoucherGrantRecordComponent } from './add-voucher-grant-record/add-voucher-grant-record.component';
import { EditVoucherGrantRecordComponent } from './edit-voucher-grant-record/edit-voucher-grant-record.component';
import { FormVoucherGrantRecordComponent } from './form-voucher-grant-record/form-voucher-grant-record.component';
import { ViewVoucherGrantRecordComponent } from './view-voucher-grant-record/view-voucher-grant-record.component';
import { ComponentsModule } from "../../components/components.module";
import { CouponGrantRecordService } from '../../services/coupon-grant-record.service';

const COMPONENTS = [
  ListVoucherGrantRecordComponent,
  AddVoucherGrantRecordComponent,
  EditVoucherGrantRecordComponent,
  FormVoucherGrantRecordComponent,
  ViewVoucherGrantRecordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
VoucherGrantRecordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CouponGrantRecordService]
})
export class VoucherGrantRecordModule { }
