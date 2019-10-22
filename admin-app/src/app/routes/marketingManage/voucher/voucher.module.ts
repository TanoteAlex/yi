import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {VoucherRoutingModule} from './voucher-routing.module';
import {ListVoucherComponent} from './list-voucher/list-voucher.component';
import {AddVoucherComponent} from './add-voucher/add-voucher.component';
import {EditVoucherComponent} from './edit-voucher/edit-voucher.component';
import {FormVoucherComponent} from './form-voucher/form-voucher.component';
import {ViewVoucherComponent} from './view-voucher/view-voucher.component';
import {ComponentsModule} from "../../components/components.module";
import {CouponService} from '../../services/coupon.service';
import {CommodityService} from "../../services/commodity.service";
import {MemberLevelService} from "../../services/member-level.service";
import {CouponReceiveService} from "../../services/coupon-receive.service";

const COMPONENTS = [
    ListVoucherComponent,
    AddVoucherComponent,
    EditVoucherComponent,
    FormVoucherComponent,
    ViewVoucherComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        VoucherRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [CouponService, CommodityService, MemberLevelService, CouponReceiveService]
})
export class VoucherModule { }
