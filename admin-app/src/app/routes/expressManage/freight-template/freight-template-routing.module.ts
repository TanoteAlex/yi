import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListFreightTemplateComponent} from './list-freight-template/list-freight-template.component';
import {AddFreightTemplateComponent} from './add-freight-template/add-freight-template.component';
import {EditFreightTemplateComponent} from './edit-freight-template/edit-freight-template.component';
import {ViewFreightTemplateComponent} from './view-freight-template/view-freight-template.component';
import {FreightTemplateService} from '../../services/freight-template.service';
import {AreaService} from "../../services/area.service";


const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: 'list', component: ListFreightTemplateComponent, data: {breadcrumb: '列表'}},
  {path: 'add', component: AddFreightTemplateComponent, data: {breadcrumb: '新增'}},
  {path: 'edit/:objId', component: EditFreightTemplateComponent, data: {breadcrumb: '编辑'}},
  {path: 'view/:objId', component: ViewFreightTemplateComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [FreightTemplateService,AreaService],
})
export class FreightTemplateRoutingModule {
}
