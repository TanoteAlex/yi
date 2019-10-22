

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListNationalGroupComponent} from './list-national-group/list-national-group.component';
import {AddNationalGroupComponent} from './add-national-group/add-national-group.component';
import {EditNationalGroupComponent} from './edit-national-group/edit-national-group.component';
import {ViewNationalGroupComponent} from './view-national-group/view-national-group.component';
import {NationalGroupService} from '../../services/national-group.service';
import {ProductService} from "../../services/product.service";
import {CouponService} from "../../services/coupon.service";
import {ListNationalGroupRecordComponent} from "./list-national-group-record/list-national-group-record.component";
import {NationalGroupRecordService} from "../../services/national-group-record.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListNationalGroupComponent, data: {breadcrumb: '列表'}},
    {path: 'recordList', component: ListNationalGroupRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddNationalGroupComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditNationalGroupComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewNationalGroupComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [NationalGroupService,ProductService,CouponService,NationalGroupRecordService],
})
export class NationalGroupRoutingModule {
}
