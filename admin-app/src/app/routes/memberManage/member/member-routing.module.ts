import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListMemberComponent} from './list-member/list-member.component';
import {AddMemberComponent} from './add-member/add-member.component';
import {EditMemberComponent} from './edit-member/edit-member.component';
import {ViewMemberComponent} from './view-member/view-member.component';
import {MemberService} from "../../services/member.service";
import {MemberLevelService} from "../../services/member-level.service";
import {CommunityService} from "../../services/community.service";
import {MemberCommissionService} from "../../services/memberCommission.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListMemberComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddMemberComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditMemberComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewMemberComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [MemberService,MemberLevelService,CommunityService,MemberCommissionService],
})
export class MemberRoutingModule {
}
