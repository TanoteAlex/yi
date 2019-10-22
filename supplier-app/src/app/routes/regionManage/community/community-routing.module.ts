import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListCommunityComponent} from './list-community/list-community.component';
import {AddCommunityComponent} from './add-community/add-community.component';
import {EditCommunityComponent} from './edit-community/edit-community.component';
import {ViewCommunityComponent} from './view-community/view-community.component';
import {CommunityService} from '../../services/community.service';
import {MemberService} from "../../services/member.service";

const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCommunityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCommunityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCommunityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCommunityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CommunityService,MemberService],
})
export class CommunityRoutingModule {
}
