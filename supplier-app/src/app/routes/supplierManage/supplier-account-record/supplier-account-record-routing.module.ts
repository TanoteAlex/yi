

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListSupplierAccountRecordComponent} from './list-supplier-account-record/list-supplier-account-record.component';
import {AddSupplierAccountRecordComponent} from './add-supplier-account-record/add-supplier-account-record.component';
import {EditSupplierAccountRecordComponent} from './edit-supplier-account-record/edit-supplier-account-record.component';
import {ViewSupplierAccountRecordComponent} from './view-supplier-account-record/view-supplier-account-record.component';
import {SupplierAccountRecordService} from '../../services/supplier-account-record.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierAccountRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSupplierAccountRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSupplierAccountRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSupplierAccountRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierAccountRecordService],
})
export class SupplierAccountRecordRoutingModule {
}