import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListSupplierSurveyComponent} from './list-supplier-survey/list-supplier-survey.component';
import {SupplierService} from '../../services/supplier.service';
import {HomepageService} from "../../services/homepage-service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierSurveyComponent, data: {breadcrumb: '列表'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierService,HomepageService],
})
export class SupplierSurveyRoutingModule {
}
