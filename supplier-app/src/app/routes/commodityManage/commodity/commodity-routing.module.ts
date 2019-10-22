import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListCommodityComponent} from './list-commodity/list-commodity.component';
import {AddCommodityComponent} from './add-commodity/add-commodity.component';
import {EditCommodityComponent} from './edit-commodity/edit-commodity.component';
import {ViewCommodityComponent} from './view-commodity/view-commodity.component';
import {CommodityService} from '../../services/commodity.service';
import {FreightTemplateService} from "../../services/freight-template.service";
import {SupplierService} from "../../services/supplier.service";
import {RegionService} from "../../services/region.service";
import {CategoryService} from "../../services/category.service";
import {MemberLevelService} from "../../services/member-level.service";
import {FreightTemplateConfigService} from "../../services/freight-template-config.service";

const routes: Routes = [

    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListCommodityComponent, data: { breadcrumb: '商品列表', title: '列表' } },
    { path: 'add', component: AddCommodityComponent, data: { breadcrumb: '新增商品', title: '新增' } },
    { path: 'edit/:objId', component: EditCommodityComponent, data: { breadcrumb: '编辑商品', title: '编辑' } },
    { path: 'view/:objId', component: ViewCommodityComponent, data: { breadcrumb: '详情商品', title: '详情' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [CommodityService, FreightTemplateConfigService, SupplierService, RegionService, CategoryService, MemberLevelService],
})
export class CommodityRoutingModule {
}
