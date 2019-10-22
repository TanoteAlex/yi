

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListJoinGroupBuyComponent} from './list-join-group-buy/list-join-group-buy.component';
import {AddJoinGroupBuyComponent} from './add-join-group-buy/add-join-group-buy.component';
import {EditJoinGroupBuyComponent} from './edit-join-group-buy/edit-join-group-buy.component';
import {ViewJoinGroupBuyComponent} from './view-join-group-buy/view-join-group-buy.component';
import {JoinGroupBuyService} from '../../services/join-group-buy.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListJoinGroupBuyComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddJoinGroupBuyComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditJoinGroupBuyComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewJoinGroupBuyComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [JoinGroupBuyService],
})
export class JoinGroupBuyRoutingModule {
}