import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListUserComponent} from './list-user/list-user.component';
import {AddUserComponent} from './add-user/add-user.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {ViewUserComponent} from './view-user/view-user.component';
import {UserService} from "../../services/user.service";
import {RoleService} from "../../services/role.service";
import {DeptService} from "../../services/dept.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListUserComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddUserComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditUserComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewUserComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [UserService,RoleService,DeptService],
})
export class UserRoutingModule {
}
