

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListQuestionTypeComponent} from './list-question-type/list-question-type.component';
import {AddQuestionTypeComponent} from './add-question-type/add-question-type.component';
import {EditQuestionTypeComponent} from './edit-question-type/edit-question-type.component';
import {ViewQuestionTypeComponent} from './view-question-type/view-question-type.component';
import {QuestionTypeService} from '../../services/question-type.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListQuestionTypeComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddQuestionTypeComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditQuestionTypeComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewQuestionTypeComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [QuestionTypeService],
})
export class QuestionTypeRoutingModule {
}