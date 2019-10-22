import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {SupplierCheckAccountRoutingModule} from './supplier-check-account-routing.module';
import {ListSupplierCheckAccountComponent} from './list-supplier-check-account/list-supplier-check-account.component';
import {AddSupplierCheckAccountComponent} from './add-supplier-check-account/add-supplier-check-account.component';
import {EditSupplierCheckAccountComponent} from './edit-supplier-check-account/edit-supplier-check-account.component';
import {FormSupplierCheckAccountComponent} from './form-supplier-check-account/form-supplier-check-account.component';
import {ViewSupplierCheckAccountComponent} from './view-supplier-check-account/view-supplier-check-account.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierCheckAccountService} from '../../services/supplier-check-account.service';

const COMPONENTS = [
  ListSupplierCheckAccountComponent,
  AddSupplierCheckAccountComponent,
  EditSupplierCheckAccountComponent,
  FormSupplierCheckAccountComponent,
  ViewSupplierCheckAccountComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SupplierCheckAccountRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierCheckAccountService]
})
export class SupplierCheckAccountModule { }
