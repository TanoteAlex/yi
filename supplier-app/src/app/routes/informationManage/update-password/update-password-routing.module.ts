import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListUpdatePasswordComponent} from './list-update-password/list-update-password.component';
import {SupplierService} from '../../services/supplier.service';
import {UserService} from "../../services/user.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListUpdatePasswordComponent, data: {breadcrumb: '列表'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierService,UserService],
})
export class UpdatePasswordRoutingModule {
}
