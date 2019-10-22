import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {AccountInformationRoutingModule} from './account-information-routing.module';
import {ListAccountInformationComponent} from './list-account-information/list-account-information.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierService} from '../../services/supplier.service';
import {UserService} from "../../services/user.service";

const COMPONENTS = [
  ListAccountInformationComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
AccountInformationRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierService,UserService]
})
export class AccountInformationModule { }
