


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { SupplierAccountRoutingModule } from './supplier-account-routing.module';
import { ListSupplierAccountComponent } from './list-supplier-account/list-supplier-account.component';
import { AddSupplierAccountComponent } from './add-supplier-account/add-supplier-account.component';
import { EditSupplierAccountComponent } from './edit-supplier-account/edit-supplier-account.component';
import { FormSupplierAccountComponent } from './form-supplier-account/form-supplier-account.component';
import { ViewSupplierAccountComponent } from './view-supplier-account/view-supplier-account.component';
import { ComponentsModule } from "../../components/components.module";
import { SupplierAccountService } from '../../services/supplier-account.service';

const COMPONENTS = [
  ListSupplierAccountComponent,
  AddSupplierAccountComponent,
  EditSupplierAccountComponent,
  FormSupplierAccountComponent,
  ViewSupplierAccountComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SupplierAccountRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierAccountService]
})
export class SupplierAccountModule { }