import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ListGroupBuyActivityComponent} from './list-group-buy-activity/list-group-buy-activity.component';
import {AddGroupBuyActivityComponent} from './add-group-buy-activity/add-group-buy-activity.component';
import {EditGroupBuyActivityComponent} from './edit-group-buy-activity/edit-group-buy-activity.component';
import {ViewGroupBuyActivityComponent} from './view-group-buy-activity/view-group-buy-activity.component';
import {GroupBuyActivityService} from '../../services/group-buy-activity.service';
import {ProductService} from "../../services/product.service";
import {MemberLevelService} from "../../services/member-level.service";
import {UserService} from "../../services/user.service";
import {CommodityService} from "../../services/commodity.service";


const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: 'list', component: ListGroupBuyActivityComponent, data: {breadcrumb: '列表'}},
  {path: 'add', component: AddGroupBuyActivityComponent, data: {breadcrumb: '新增'}},
  {path: 'edit/:objId', component: EditGroupBuyActivityComponent, data: {breadcrumb: '编辑'}},
  {path: 'view/:objId', component: ViewGroupBuyActivityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [GroupBuyActivityService, ProductService, MemberLevelService, UserService, CommodityService],
})
export class GroupBuyActivityRoutingModule {
}
