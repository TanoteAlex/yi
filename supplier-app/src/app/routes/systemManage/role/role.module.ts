import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {RoleRoutingModule} from './role-routing.module';
import {ListRoleComponent} from './list-role/list-role.component';
import {AddRoleComponent} from './add-role/add-role.component';
import {EditRoleComponent} from './edit-role/edit-role.component';
import {FormRoleComponent} from './form-role/form-role.component';
import {ViewRoleComponent} from './view-role/view-role.component';
import {ComponentsModule} from "../../components/components.module";
import {RoleService} from '../../services/role.service';

const COMPONENTS = [
  ListRoleComponent,
  AddRoleComponent,
  EditRoleComponent,
  FormRoleComponent,
  ViewRoleComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
RoleRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[RoleService]
})
export class RoleModule { }
