

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListAttributeGroupComponent} from './list-attribute-group/list-attribute-group.component';
import {AddAttributeGroupComponent} from './add-attribute-group/add-attribute-group.component';
import {EditAttributeGroupComponent} from './edit-attribute-group/edit-attribute-group.component';
import {ViewAttributeGroupComponent} from './view-attribute-group/view-attribute-group.component';
import {AttributeGroupService} from '../../services/attribute-group.service';
import {CommodityService} from "../../services/commodity.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAttributeGroupComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAttributeGroupComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAttributeGroupComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAttributeGroupComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AttributeGroupService,CommodityService],
})
export class AttributeGroupRoutingModule {
}
