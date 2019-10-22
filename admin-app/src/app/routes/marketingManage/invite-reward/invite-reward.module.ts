import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { InviteRewardRoutingModule } from './invite-reward-routing.module';
import { ListInviteRewardComponent } from './list-invite-reward/list-invite-reward.component';
import { AddInviteRewardComponent } from './add-invite-reward/add-invite-reward.component';
import { EditInviteRewardComponent } from './edit-invite-reward/edit-invite-reward.component';
import { FormInviteRewardComponent } from './form-invite-reward/form-invite-reward.component';
import { ViewInviteRewardComponent } from './view-invite-reward/view-invite-reward.component';
import { ComponentsModule } from '../../components/components.module';
import { InviteRewardService } from '../../services/invite-reward.service';
import { InviteActivityService } from '../../services/invite-activity.service';
import { RewardService } from '../../services/reward.service';

const COMPONENTS = [
  ListInviteRewardComponent,
  AddInviteRewardComponent,
  EditInviteRewardComponent,
  FormInviteRewardComponent,
  ViewInviteRewardComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    InviteRewardRoutingModule,
    ComponentsModule,
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers: [InviteRewardService,InviteActivityService,RewardService],
})
export class InviteRewardModule {
}
