

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListLogisticsAddressComponent} from './list-logistics-address/list-logistics-address.component';
import {AddLogisticsAddressComponent} from './add-logistics-address/add-logistics-address.component';
import {EditLogisticsAddressComponent} from './edit-logistics-address/edit-logistics-address.component';
import {ViewLogisticsAddressComponent} from './view-logistics-address/view-logistics-address.component';
import {LogisticsAddressService} from '../../services/logistics-address.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListLogisticsAddressComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddLogisticsAddressComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditLogisticsAddressComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewLogisticsAddressComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [LogisticsAddressService],
})
export class LogisticsAddressRoutingModule {
}