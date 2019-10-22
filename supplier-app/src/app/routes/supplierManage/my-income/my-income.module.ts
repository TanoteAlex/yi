import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {MyIncomeRoutingModule} from './my-income-routing.module';
import {ComponentsModule} from "../../components/components.module";
import {PlatformSaleStatService} from '../../services/platform-sale-stat.service';
import {ListMyIncomeComponent} from "./list-my-income/list-my-income.component";
import {SupplierAccountService} from "../../services/supplier-account.service";
import {SaleOrderItemService} from "../../services/sale-order-item.service";

const COMPONENTS = [
    ListMyIncomeComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
    imports: [
        SharedModule,
        MyIncomeRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [PlatformSaleStatService, SupplierAccountService, SaleOrderItemService]
})
export class MyIncomeModule {
}
