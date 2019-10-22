

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListArticleCommentComponent} from './list-article-comment/list-article-comment.component';
import {AddArticleCommentComponent} from './add-article-comment/add-article-comment.component';
import {EditArticleCommentComponent} from './edit-article-comment/edit-article-comment.component';
import {ViewArticleCommentComponent} from './view-article-comment/view-article-comment.component';
import {ArticleCommentService} from '../../services/article-comment.service';
import {ArticleService} from "../../services/article.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListArticleCommentComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddArticleCommentComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditArticleCommentComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewArticleCommentComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [ArticleCommentService],
})
export class ArticleCommentRoutingModule {
}
