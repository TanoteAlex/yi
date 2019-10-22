

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListIntegralRecordComponent} from './list-integral-record/list-integral-record.component';
import {AddIntegralRecordComponent} from './add-integral-record/add-integral-record.component';
import {EditIntegralRecordComponent} from './edit-integral-record/edit-integral-record.component';
import {ViewIntegralRecordComponent} from './view-integral-record/view-integral-record.component';
import {IntegralRecordService} from '../../services/integral-record.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListIntegralRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddIntegralRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditIntegralRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewIntegralRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [IntegralRecordService],
})
export class IntegralRecordRoutingModule {
}