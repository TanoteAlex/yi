

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListSeckillComponent} from './list-seckill/list-seckill.component';
import {AddSeckillComponent} from './add-seckill/add-seckill.component';
import {EditSeckillComponent} from './edit-seckill/edit-seckill.component';
import {ViewSeckillComponent} from './view-seckill/view-seckill.component';
import {SeckillService} from '../../services/seckill.service';
import {ProductService} from "../../services/product.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSeckillComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSeckillComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSeckillComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSeckillComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SeckillService,ProductService],
})
export class SeckillRoutingModule {
}
