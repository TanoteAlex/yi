

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListLoginRecordComponent} from './list-login-record/list-login-record.component';
import {AddLoginRecordComponent} from './add-login-record/add-login-record.component';
import {EditLoginRecordComponent} from './edit-login-record/edit-login-record.component';
import {ViewLoginRecordComponent} from './view-login-record/view-login-record.component';
import {LoginRecordService} from '../../services/login-record.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListLoginRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddLoginRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditLoginRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewLoginRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [LoginRecordService],
})
export class LoginRecordRoutingModule {
}