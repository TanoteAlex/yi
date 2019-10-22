import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListInviteRewardComponent } from './list-invite-reward/list-invite-reward.component';
import { AddInviteRewardComponent } from './add-invite-reward/add-invite-reward.component';
import { EditInviteRewardComponent } from './edit-invite-reward/edit-invite-reward.component';
import { ViewInviteRewardComponent } from './view-invite-reward/view-invite-reward.component';
import { InviteRewardService } from '../../services/invite-reward.service';


const routes: Routes = [
  { path: '', redirectTo: 'list', pathMatch: 'full' },
  { path: 'list', component: ListInviteRewardComponent, data: { breadcrumb: '列表' } },
  { path: 'add', component: AddInviteRewardComponent, data: { breadcrumb: '新增' } },
  { path: 'edit/:objId', component: EditInviteRewardComponent, data: { breadcrumb: '编辑' } },
  { path: 'view/:objId', component: ViewInviteRewardComponent, data: { breadcrumb: '详情' } },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [InviteRewardService],
})
export class InviteRewardRoutingModule {
}
