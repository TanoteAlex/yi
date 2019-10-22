

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListQuestionComponent} from './list-question/list-question.component';
import {AddQuestionComponent} from './add-question/add-question.component';
import {EditQuestionComponent} from './edit-question/edit-question.component';
import {ViewQuestionComponent} from './view-question/view-question.component';
import {QuestionService} from '../../services/question.service';
import {QuestionTypeService} from "../../services/question-type.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListQuestionComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddQuestionComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditQuestionComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewQuestionComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [QuestionService,QuestionTypeService],
})
export class QuestionRoutingModule {
}
