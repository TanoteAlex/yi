import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {UserRoutingModule} from './user-routing.module';
import {ListUserComponent} from './list-user/list-user.component';
import {AddUserComponent} from './add-user/add-user.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {FormUserComponent} from './form-user/form-user.component';
import {ViewUserComponent} from './view-user/view-user.component';
import {ComponentsModule} from "../../components/components.module";
import {DeptService} from '../../services/dept.service';
import {UserService} from "../../services/user.service";

const COMPONENTS = [
  ListUserComponent,
  AddUserComponent,
  EditUserComponent,
  FormUserComponent,
  ViewUserComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
UserRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[UserService,DeptService]
})
export class UserModule { }
