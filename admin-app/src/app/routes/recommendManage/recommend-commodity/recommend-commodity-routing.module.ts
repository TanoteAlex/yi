

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListRecommendCommodityComponent} from './list-recommend-commodity/list-recommend-commodity.component';
import {AddRecommendCommodityComponent} from './add-recommend-commodity/add-recommend-commodity.component';
import {EditRecommendCommodityComponent} from './edit-recommend-commodity/edit-recommend-commodity.component';
import {ViewRecommendCommodityComponent} from './view-recommend-commodity/view-recommend-commodity.component';
import {RecommendCommodityService} from '../../services/recommend-commodity.service';


const routes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListRecommendCommodityComponent, data: { breadcrumb: '列表' } },
    { path: 'add', component: AddRecommendCommodityComponent, data: { breadcrumb: '新增' } },
    { path: 'edit/:objId', component: EditRecommendCommodityComponent, data: { breadcrumb: '编辑' } },
    { path: 'view/:objId', component: ViewRecommendCommodityComponent, data: { breadcrumb: '详情' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [RecommendCommodityService],
})
export class RecommendCommodityRoutingModule {
}