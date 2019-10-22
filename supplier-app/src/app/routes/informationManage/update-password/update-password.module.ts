import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {ListUpdatePasswordComponent} from './list-update-password/list-update-password.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierService} from '../../services/supplier.service';
import {UserService} from "../../services/user.service";
import {UpdatePasswordRoutingModule} from "./update-password-routing.module";

const COMPONENTS = [
  ListUpdatePasswordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
UpdatePasswordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierService,UserService]
})
export class UpdatePasswordModule { }
