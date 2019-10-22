import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListRoleComponent} from './list-role/list-role.component';
import {AddRoleComponent} from './add-role/add-role.component';
import {EditRoleComponent} from './edit-role/edit-role.component';
import {ViewRoleComponent} from './view-role/view-role.component';
import {RoleService} from '../../services/role.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRoleComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRoleComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRoleComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRoleComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RoleService],
})
export class RoleRoutingModule {
}
