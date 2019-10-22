

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListSupplierAccountComponent} from './list-supplier-account/list-supplier-account.component';
import {AddSupplierAccountComponent} from './add-supplier-account/add-supplier-account.component';
import {EditSupplierAccountComponent} from './edit-supplier-account/edit-supplier-account.component';
import {ViewSupplierAccountComponent} from './view-supplier-account/view-supplier-account.component';
import {SupplierAccountService} from '../../services/supplier-account.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierAccountComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSupplierAccountComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSupplierAccountComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSupplierAccountComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierAccountService],
})
export class SupplierAccountRoutingModule {
}