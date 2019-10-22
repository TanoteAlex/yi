


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { InviteActivityRoutingModule } from './invite-activity-routing.module';
import { ListInviteActivityComponent } from './list-invite-activity/list-invite-activity.component';
import { AddInviteActivityComponent } from './add-invite-activity/add-invite-activity.component';
import { EditInviteActivityComponent } from './edit-invite-activity/edit-invite-activity.component';
import { FormInviteActivityComponent } from './form-invite-activity/form-invite-activity.component';
import { ViewInviteActivityComponent } from './view-invite-activity/view-invite-activity.component';
import { ComponentsModule } from "../../components/components.module";
import { InviteActivityService } from '../../services/invite-activity.service';

const COMPONENTS = [
  ListInviteActivityComponent,
  AddInviteActivityComponent,
  EditInviteActivityComponent,
  FormInviteActivityComponent,
  ViewInviteActivityComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
InviteActivityRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[InviteActivityService]
})
export class InviteActivityModule { }