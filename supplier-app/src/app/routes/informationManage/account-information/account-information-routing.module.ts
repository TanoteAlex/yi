import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListAccountInformationComponent} from './list-account-information/list-account-information.component';
import {SupplierService} from '../../services/supplier.service';
import {UserService} from "../../services/user.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAccountInformationComponent, data: {breadcrumb: '列表'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierService,UserService],
})
export class AccountInformationRoutingModule {
}
