import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListArticleComponent} from './list-article/list-article.component';
import {AddArticleComponent} from './add-article/add-article.component';
import {EditArticleComponent} from './edit-article/edit-article.component';
import {ViewArticleComponent} from './view-article/view-article.component';
import {ArticleService} from "../../services/article.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListArticleComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddArticleComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditArticleComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewArticleComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [ArticleService],
})
export class ArticleRoutingModule {
}
