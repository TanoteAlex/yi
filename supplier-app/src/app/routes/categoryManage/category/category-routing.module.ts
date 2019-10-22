

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListCategoryComponent} from './list-category/list-category.component';
import {AddCategoryComponent} from './add-category/add-category.component';
import {EditCategoryComponent} from './edit-category/edit-category.component';
import {ViewCategoryComponent} from './view-category/view-category.component';
import {CategoryService} from '../../services/category.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCategoryComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCategoryComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCategoryComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCategoryComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CategoryService],
})
export class CategoryRoutingModule {
}