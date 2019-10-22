

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListStockComponent} from './list-stock/list-stock.component';
import {AddStockComponent} from './add-stock/add-stock.component';
import {EditStockComponent} from './edit-stock/edit-stock.component';
import {ViewStockComponent} from './view-stock/view-stock.component';
import {StockService} from '../../services/stock.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListStockComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddStockComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditStockComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewStockComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [StockService],
})
export class StockRoutingModule {
}