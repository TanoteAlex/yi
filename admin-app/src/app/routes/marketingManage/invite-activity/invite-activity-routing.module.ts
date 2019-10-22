

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListInviteActivityComponent} from './list-invite-activity/list-invite-activity.component';
import {AddInviteActivityComponent} from './add-invite-activity/add-invite-activity.component';
import {EditInviteActivityComponent} from './edit-invite-activity/edit-invite-activity.component';
import {ViewInviteActivityComponent} from './view-invite-activity/view-invite-activity.component';
import {InviteActivityService} from '../../services/invite-activity.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListInviteActivityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddInviteActivityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditInviteActivityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewInviteActivityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [InviteActivityService],
})
export class InviteActivityRoutingModule {
}