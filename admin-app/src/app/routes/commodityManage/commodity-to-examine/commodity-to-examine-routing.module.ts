import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListCommodityToExamineComponent} from './list-commodity-to-examine/list-commodity-to-examine.component';
import {ViewCommodityToExamineComponent} from './view-commodity-to-examine/view-commodity-to-examine.component';
import {CommodityService} from '../../services/commodity.service';

const routes: Routes = [

    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListCommodityToExamineComponent, data: { breadcrumb: '商品列表', title: '列表' } },
    { path: 'view/:objId', component: ViewCommodityToExamineComponent, data: { breadcrumb: '详情商品', title: '详情' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [CommodityService],
})
export class CommodityToExamineRoutingModule {

}
