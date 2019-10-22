import {NgModule} from '@angular/core';
import {SharedModule} from '../../../shared/shared.module';
import {IntegralTaskRoutingModule} from './integral-task-routing.module';
import {ListIntegralTaskComponent} from './list-integral-task/list-integral-task.component';
import {AddIntegralTaskComponent} from './add-integral-task/add-integral-task.component';
import {EditIntegralTaskComponent} from './edit-integral-task/edit-integral-task.component';
import {FormIntegralTaskComponent} from './form-integral-task/form-integral-task.component';
import {ViewIntegralTaskComponent} from './view-integral-task/view-integral-task.component';
import {ComponentsModule} from "../../components/components.module";
import {IntegralTaskService} from "../../services/integral-task.service";

const COMPONENTS = [
  ListIntegralTaskComponent,
  AddIntegralTaskComponent,
  EditIntegralTaskComponent,
  FormIntegralTaskComponent,
  ViewIntegralTaskComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
IntegralTaskRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[IntegralTaskService]
})
export class IntegralTaskModule {  }


