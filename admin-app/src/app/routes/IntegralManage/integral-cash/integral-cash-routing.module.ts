

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListIntegralCashComponent} from './list-integral-cash/list-integral-cash.component';
import {AddIntegralCashComponent} from './add-integral-cash/add-integral-cash.component';
import {EditIntegralCashComponent} from './edit-integral-cash/edit-integral-cash.component';
import {ViewIntegralCashComponent} from './view-integral-cash/view-integral-cash.component';
import {IntegralCashService} from '../../services/integral-cash.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListIntegralCashComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddIntegralCashComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditIntegralCashComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewIntegralCashComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [IntegralCashService],
})
export class IntegralCashRoutingModule {
}