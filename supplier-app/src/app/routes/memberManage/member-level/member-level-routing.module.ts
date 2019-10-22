import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListMemberLevelComponent} from './list-member-level/list-member-level.component';
import {AddMemberLevelComponent} from './add-member-level/add-member-level.component';
import {EditMemberLevelComponent} from './edit-member-level/edit-member-level.component';
import {ViewMemberLevelComponent} from './view-member-level/view-member-level.component';
import {MemberLevelService} from '../../services/member-level.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListMemberLevelComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddMemberLevelComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditMemberLevelComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewMemberLevelComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [MemberLevelService],
})
export class MemberLevelRoutingModule {
}
