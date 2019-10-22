

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListBasicInfoComponent} from './list-basic-info/list-basic-info.component';
import {AddBasicInfoComponent} from './add-basic-info/add-basic-info.component';
import {EditBasicInfoComponent} from './edit-basic-info/edit-basic-info.component';
import {ViewBasicInfoComponent} from './view-basic-info/view-basic-info.component';
import {BasicInfoService} from '../../services/basic-info.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListBasicInfoComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddBasicInfoComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditBasicInfoComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewBasicInfoComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [BasicInfoService],
})
export class BasicInfoRoutingModule {
}