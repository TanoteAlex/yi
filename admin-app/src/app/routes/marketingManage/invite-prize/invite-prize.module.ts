


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { InvitePrizeRoutingModule } from './invite-prize-routing.module';
import { ListInvitePrizeComponent } from './list-invite-prize/list-invite-prize.component';
import { AddInvitePrizeComponent } from './add-invite-prize/add-invite-prize.component';
import { EditInvitePrizeComponent } from './edit-invite-prize/edit-invite-prize.component';
import { FormInvitePrizeComponent } from './form-invite-prize/form-invite-prize.component';
import { ViewInvitePrizeComponent } from './view-invite-prize/view-invite-prize.component';
import { ComponentsModule } from "../../components/components.module";
import { InvitePrizeService } from '../../services/invite-prize.service';

const COMPONENTS = [
  ListInvitePrizeComponent,
  AddInvitePrizeComponent,
  EditInvitePrizeComponent,
  FormInvitePrizeComponent,
  ViewInvitePrizeComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
InvitePrizeRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[InvitePrizeService]
})
export class InvitePrizeModule { }