import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListRecommendComponent} from './list-recommend/list-recommend.component';
import {AddRecommendComponent} from './add-recommend/add-recommend.component';
import {EditRecommendComponent} from './edit-recommend/edit-recommend.component';
import {ViewRecommendComponent} from './view-recommend/view-recommend.component';
import {RecommendService} from '../../services/recommend.service';
import {CommodityService} from "../../services/commodity.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRecommendComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRecommendComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRecommendComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRecommendComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RecommendService,CommodityService],
})
export class RecommendRoutingModule {
}
