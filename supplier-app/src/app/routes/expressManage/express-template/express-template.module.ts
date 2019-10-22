import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {ExpressTemplateRoutingModule} from './express-template-routing.module';
import {ListExpressTemplateComponent} from './list-express-template/list-express-template.component';
import {AddExpressTemplateComponent} from './add-express-template/add-express-template.component';
import {EditExpressTemplateComponent} from './edit-express-template/edit-express-template.component';
import {FormExpressTemplateComponent} from './form-express-template/form-express-template.component';
import {ViewExpressTemplateComponent} from './view-express-template/view-express-template.component';
import {ComponentsModule} from "../../components/components.module";
import {ExpressTemplateService} from '../../services/express-template.service';

const COMPONENTS = [
  ListExpressTemplateComponent,
  AddExpressTemplateComponent,
  EditExpressTemplateComponent,
  FormExpressTemplateComponent,
  ViewExpressTemplateComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
ExpressTemplateRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[ExpressTemplateService]
})
export class ExpressTemplateModule { }
