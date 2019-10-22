import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListIntegralTaskComponent} from './list-integral-task/list-integral-task.component';
import {AddIntegralTaskComponent} from './add-integral-task/add-integral-task.component';
import {EditIntegralTaskComponent} from './edit-integral-task/edit-integral-task.component';
import {ViewIntegralTaskComponent} from './view-integral-task/view-integral-task.component';
import {IntegralTaskService} from "../../services/integral-task.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListIntegralTaskComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddIntegralTaskComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditIntegralTaskComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewIntegralTaskComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [IntegralTaskService],
})
export class IntegralTaskRoutingModule {
}
