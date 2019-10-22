

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListAfterSaleReasonComponent} from './list-after-sale-reason/list-after-sale-reason.component';
import {AddAfterSaleReasonComponent} from './add-after-sale-reason/add-after-sale-reason.component';
import {EditAfterSaleReasonComponent} from './edit-after-sale-reason/edit-after-sale-reason.component';
import {ViewAfterSaleReasonComponent} from './view-after-sale-reason/view-after-sale-reason.component';
import {AfterSaleReasonService} from '../../services/after-sale-reason.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAfterSaleReasonComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAfterSaleReasonComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAfterSaleReasonComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAfterSaleReasonComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AfterSaleReasonService],
})
export class AfterSaleReasonRoutingModule {
}