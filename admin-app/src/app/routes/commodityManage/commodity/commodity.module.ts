import {NgModule} from '@angular/core';
import {SharedModule} from 'app/shared/shared.module';
import {CommodityRoutingModule} from './commodity-routing.module';
import {ListCommodityComponent} from './list-commodity/list-commodity.component';
import {AddCommodityComponent} from './add-commodity/add-commodity.component';
import {EditCommodityComponent} from './edit-commodity/edit-commodity.component';
import {FormCommodityComponent} from './form-commodity/form-commodity.component';
import {ViewCommodityComponent} from './view-commodity/view-commodity.component';
import {ComponentsModule} from "../../components/components.module";
import {CommodityService} from "../../services/commodity.service";
import {SupplierService} from "../../services/supplier.service";
import {RegionService} from "../../services/region.service";
import {CategoryService} from "../../services/category.service";
import {MemberLevelService} from "../../services/member-level.service";
import {FreightTemplateConfigService} from "../../services/freight-template-config.service";
import {CouponGrantConfigService} from "../../services/coupon-grant-config.service";

const COMPONENTS = [
    ListCommodityComponent,
    AddCommodityComponent,
    EditCommodityComponent,
    FormCommodityComponent,
    ViewCommodityComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        CommodityRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [CommodityService, FreightTemplateConfigService, SupplierService, RegionService, CategoryService, MemberLevelService, CouponGrantConfigService]
})
export class CommodityModule { }
