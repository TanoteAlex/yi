import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {CommunityRoutingModule} from './community-routing.module';
import {ListCommunityComponent} from './list-community/list-community.component';
import {AddCommunityComponent} from './add-community/add-community.component';
import {EditCommunityComponent} from './edit-community/edit-community.component';
import {FormCommunityComponent} from './form-community/form-community.component';
import {ViewCommunityComponent} from './view-community/view-community.component';
import {ComponentsModule} from "../../components/components.module";
import {CommunityService} from '../../services/community.service';
import {MemberService} from "../../services/member.service";

const COMPONENTS = [
  ListCommunityComponent,
  AddCommunityComponent,
  EditCommunityComponent,
  FormCommunityComponent,
  ViewCommunityComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
CommunityRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CommunityService,MemberService]
})
export class CommunityModule { }
