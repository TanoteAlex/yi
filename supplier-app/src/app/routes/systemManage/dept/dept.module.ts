import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {DeptRoutingModule} from './dept-routing.module';
import {ListDeptComponent} from './list-dept/list-dept.component';
import {AddDeptComponent} from './add-dept/add-dept.component';
import {EditDeptComponent} from './edit-dept/edit-dept.component';
import {FormDeptComponent} from './form-dept/form-dept.component';
import {ViewDeptComponent} from './view-dept/view-dept.component';
import {ComponentsModule} from "../../components/components.module";
import {DeptService} from '../../services/dept.service';

const COMPONENTS = [
  ListDeptComponent,
  AddDeptComponent,
  EditDeptComponent,
  FormDeptComponent,
  ViewDeptComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
DeptRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[DeptService]
})
export class DeptModule { }
