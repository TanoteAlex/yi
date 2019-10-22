import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {SupplierRoutingModule} from './supplier-routing.module';
import {ListSupplierComponent} from './list-supplier/list-supplier.component';
import {AddSupplierComponent} from './add-supplier/add-supplier.component';
import {EditSupplierComponent} from './edit-supplier/edit-supplier.component';
import {FormSupplierComponent} from './form-supplier/form-supplier.component';
import {ViewSupplierComponent} from './view-supplier/view-supplier.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierService} from '../../services/supplier.service';
import {UserService} from "../../services/user.service";
import {DistrictPipe} from "../../pipes/district/district";

const COMPONENTS = [
  ListSupplierComponent,
  AddSupplierComponent,
  EditSupplierComponent,
  FormSupplierComponent,
  ViewSupplierComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SupplierRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierService,UserService]
})
export class SupplierModule { }
