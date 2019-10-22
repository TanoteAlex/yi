import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListPositionComponent} from './list-position/list-position.component';
import {AddPositionComponent} from './add-position/add-position.component';
import {EditPositionComponent} from './edit-position/edit-position.component';
import {ViewPositionComponent} from './view-position/view-position.component';
import {PositionService} from '../../services/position.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListPositionComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddPositionComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditPositionComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewPositionComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [PositionService],
})
export class PositionRoutingModule {
}
