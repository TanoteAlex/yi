

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListAccountRecordComponent} from './list-account-record/list-account-record.component';
import {AddAccountRecordComponent} from './add-account-record/add-account-record.component';
import {EditAccountRecordComponent} from './edit-account-record/edit-account-record.component';
import {ViewAccountRecordComponent} from './view-account-record/view-account-record.component';
import {AccountRecordService} from '../../services/account-record.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAccountRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAccountRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAccountRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAccountRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AccountRecordService],
})
export class AccountRecordRoutingModule {
}