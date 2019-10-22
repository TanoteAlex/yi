

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListAreaColumnComponent} from './list-area-column/list-area-column.component';
import {AddAreaColumnComponent} from './add-area-column/add-area-column.component';
import {EditAreaColumnComponent} from './edit-area-column/edit-area-column.component';
import {ViewAreaColumnComponent} from './view-area-column/view-area-column.component';
import {AreaColumnService} from '../../services/area-column.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAreaColumnComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAreaColumnComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAreaColumnComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAreaColumnComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AreaColumnService],
})
export class AreaColumnRoutingModule {
}