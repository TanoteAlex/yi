import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListReturnReasonComponent} from './list-return-reason/list-return-reason.component';
import {AddReturnReasonComponent} from './add-return-reason/add-return-reason.component';
import {EditReturnReasonComponent} from './edit-return-reason/edit-return-reason.component';
import {ViewReturnReasonComponent} from './view-return-reason/view-return-reason.component';
import {ReturnReasonService} from '../../services/return-reason.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListReturnReasonComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddReturnReasonComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditReturnReasonComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewReturnReasonComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [ReturnReasonService],
})
export class ReturnReasonRoutingModule {
}
