import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {SupplierWithdrawRoutingModule} from './supplier-withdraw-routing.module';
import {ListSupplierWithdrawComponent} from './list-supplier-withdraw/list-supplier-withdraw.component';
import {AddSupplierWithdrawComponent} from './add-supplier-withdraw/add-supplier-withdraw.component';
import {EditSupplierWithdrawComponent} from './edit-supplier-withdraw/edit-supplier-withdraw.component';
import {FormSupplierWithdrawComponent} from './form-supplier-withdraw/form-supplier-withdraw.component';
import {ViewSupplierWithdrawComponent} from './view-supplier-withdraw/view-supplier-withdraw.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierWithdrawService} from '../../services/supplier-withdraw.service';

const COMPONENTS = [
    ListSupplierWithdrawComponent,
    AddSupplierWithdrawComponent,
    EditSupplierWithdrawComponent,
    FormSupplierWithdrawComponent,
    ViewSupplierWithdrawComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        SupplierWithdrawRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [SupplierWithdrawService]
})
export class SupplierWithdrawModule { }
