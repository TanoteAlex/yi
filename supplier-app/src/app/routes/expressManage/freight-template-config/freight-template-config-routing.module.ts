

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListFreightTemplateConfigComponent} from './list-freight-template-config/list-freight-template-config.component';
import {AddFreightTemplateConfigComponent} from './add-freight-template-config/add-freight-template-config.component';
import {EditFreightTemplateConfigComponent} from './edit-freight-template-config/edit-freight-template-config.component';
import {ViewFreightTemplateConfigComponent} from './view-freight-template-config/view-freight-template-config.component';
import {FreightTemplateConfigService} from '../../services/freight-template-config.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListFreightTemplateConfigComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddFreightTemplateConfigComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditFreightTemplateConfigComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewFreightTemplateConfigComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [FreightTemplateConfigService],
})
export class FreightTemplateConfigRoutingModule {
}