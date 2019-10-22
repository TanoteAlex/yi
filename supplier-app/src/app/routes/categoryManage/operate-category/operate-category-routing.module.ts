

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListOperateCategoryComponent} from './list-operate-category/list-operate-category.component';
import {AddOperateCategoryComponent} from './add-operate-category/add-operate-category.component';
import {EditOperateCategoryComponent} from './edit-operate-category/edit-operate-category.component';
import {ViewOperateCategoryComponent} from './view-operate-category/view-operate-category.component';
import {OperateCategoryService} from '../../services/operate-category.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListOperateCategoryComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddOperateCategoryComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditOperateCategoryComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewOperateCategoryComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [OperateCategoryService],
})
export class OperateCategoryRoutingModule {
}