import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {SupplierSaleStatRoutingModule} from './supplier-sale-stat-routing.module';
import {ListSupplierSaleStatComponent} from './list-supplier-sale-stat/list-supplier-sale-stat.component';
import {AddSupplierSaleStatComponent} from './add-supplier-sale-stat/add-supplier-sale-stat.component';
import {EditSupplierSaleStatComponent} from './edit-supplier-sale-stat/edit-supplier-sale-stat.component';
import {FormSupplierSaleStatComponent} from './form-supplier-sale-stat/form-supplier-sale-stat.component';
import {ViewSupplierSaleStatComponent} from './view-supplier-sale-stat/view-supplier-sale-stat.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierSaleStatService} from '../../services/supplier-sale-stat.service';

const COMPONENTS = [
    ListSupplierSaleStatComponent,
    AddSupplierSaleStatComponent,
    EditSupplierSaleStatComponent,
    FormSupplierSaleStatComponent,
    ViewSupplierSaleStatComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        SupplierSaleStatRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [SupplierSaleStatService]
})
export class SupplierSaleStatModule { }
