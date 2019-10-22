

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListBasicRuleComponent} from './list-basic-rule/list-basic-rule.component';
import {AddBasicRuleComponent} from './add-basic-rule/add-basic-rule.component';
import {EditBasicRuleComponent} from './edit-basic-rule/edit-basic-rule.component';
import {ViewBasicRuleComponent} from './view-basic-rule/view-basic-rule.component';
import {BasicRuleService} from '../../services/basic-rule.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListBasicRuleComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddBasicRuleComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditBasicRuleComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewBasicRuleComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [BasicRuleService],
})
export class BasicRuleRoutingModule {
}