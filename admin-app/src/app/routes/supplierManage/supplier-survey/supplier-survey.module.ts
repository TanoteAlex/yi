import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {SupplierSurveyRoutingModule} from './supplier-survey-routing.module';
import {ListSupplierSurveyComponent} from './list-supplier-survey/list-supplier-survey.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierService} from '../../services/supplier.service';
import {HomepageService} from "../../services/homepage-service";

const COMPONENTS = [
  ListSupplierSurveyComponent,
  ];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SupplierSurveyRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierService,HomepageService]
})
export class SupplierSurveyModule { }
