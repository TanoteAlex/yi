

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListInvitePrizeComponent} from './list-invite-prize/list-invite-prize.component';
import {AddInvitePrizeComponent} from './add-invite-prize/add-invite-prize.component';
import {EditInvitePrizeComponent} from './edit-invite-prize/edit-invite-prize.component';
import {ViewInvitePrizeComponent} from './view-invite-prize/view-invite-prize.component';
import {InvitePrizeService} from '../../services/invite-prize.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListInvitePrizeComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddInvitePrizeComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditInvitePrizeComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewInvitePrizeComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [InvitePrizeService],
})
export class InvitePrizeRoutingModule {
}