import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PlatformSaleStatService} from '../../services/platform-sale-stat.service';
import {ListMyIncomeComponent} from "./list-my-income/list-my-income.component";
import {SaleOrderItemService} from "../../services/sale-order-item.service";
import {SupplierAccountService} from "../../services/supplier-account.service";


const routes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListMyIncomeComponent, data: { breadcrumb: '我的收入' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [PlatformSaleStatService, SupplierAccountService, SaleOrderItemService],
})
export class MyIncomeRoutingModule {
}
