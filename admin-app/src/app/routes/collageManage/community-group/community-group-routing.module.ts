

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListCommunityGroupComponent} from './list-community-group/list-community-group.component';
import {AddCommunityGroupComponent} from './add-community-group/add-community-group.component';
import {EditCommunityGroupComponent} from './edit-community-group/edit-community-group.component';
import {ViewCommunityGroupComponent} from './view-community-group/view-community-group.component';
import {CommunityGroupService} from '../../services/community-group.service';
import {ProductService} from "../../services/product.service";
import {CommunityService} from "../../services/community.service";
import {CouponService} from "../../services/coupon.service";
import {ListCommunityGroupRecordComponent} from "./list-community-group-record/list-community-group-record.component";
import {CommunityGroupRecordService} from "../../services/community-group-record.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCommunityGroupComponent, data: {breadcrumb: '列表'}},
    {path: 'recordList', component: ListCommunityGroupRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCommunityGroupComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCommunityGroupComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCommunityGroupComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CommunityGroupService,ProductService,CouponService,CommunityService,CommunityGroupRecordService],
})
export class CommunityGroupRoutingModule {
}
