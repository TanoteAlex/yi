import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {MemberLevelRoutingModule} from './member-level-routing.module';
import {ListMemberLevelComponent} from './list-member-level/list-member-level.component';
import {AddMemberLevelComponent} from './add-member-level/add-member-level.component';
import {EditMemberLevelComponent} from './edit-member-level/edit-member-level.component';
import {FormMemberLevelComponent} from './form-member-level/form-member-level.component';
import {ViewMemberLevelComponent} from './view-member-level/view-member-level.component';
import {ComponentsModule} from "../../components/components.module";
import {MemberLevelService} from '../../services/member-level.service';

const COMPONENTS = [
  ListMemberLevelComponent,
  AddMemberLevelComponent,
  EditMemberLevelComponent,
  FormMemberLevelComponent,
  ViewMemberLevelComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
MemberLevelRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[MemberLevelService]
})
export class MemberLevelModule { }
