import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListSupplierSaleStatComponent} from './list-supplier-sale-stat/list-supplier-sale-stat.component';
import {AddSupplierSaleStatComponent} from './add-supplier-sale-stat/add-supplier-sale-stat.component';
import {EditSupplierSaleStatComponent} from './edit-supplier-sale-stat/edit-supplier-sale-stat.component';
import {ViewSupplierSaleStatComponent} from './view-supplier-sale-stat/view-supplier-sale-stat.component';
import {SupplierSaleStatService} from "../../services/supplier-sale-stat.service";


const routes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListSupplierSaleStatComponent, data: { breadcrumb: '列表' } },
    { path: 'add', component: AddSupplierSaleStatComponent, data: { breadcrumb: '新增' } },
    { path: 'edit/:objId', component: EditSupplierSaleStatComponent, data: { breadcrumb: '编辑' } },
    { path: 'view/:objId', component: ViewSupplierSaleStatComponent, data: { breadcrumb: '详情' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [SupplierSaleStatService],
})
export class SupplierSaleStatRoutingModule {
}
