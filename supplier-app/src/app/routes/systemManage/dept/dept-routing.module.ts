import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListDeptComponent} from './list-dept/list-dept.component';
import {AddDeptComponent} from './add-dept/add-dept.component';
import {EditDeptComponent} from './edit-dept/edit-dept.component';
import {ViewDeptComponent} from './view-dept/view-dept.component';
import {DeptService} from '../../services/dept.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListDeptComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddDeptComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditDeptComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewDeptComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [DeptService],
})
export class DeptRoutingModule {
}
