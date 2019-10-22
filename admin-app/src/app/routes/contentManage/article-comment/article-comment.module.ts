


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { ArticleCommentRoutingModule } from './article-comment-routing.module';
import { ListArticleCommentComponent } from './list-article-comment/list-article-comment.component';
import { AddArticleCommentComponent } from './add-article-comment/add-article-comment.component';
import { EditArticleCommentComponent } from './edit-article-comment/edit-article-comment.component';
import { FormArticleCommentComponent } from './form-article-comment/form-article-comment.component';
import { ViewArticleCommentComponent } from './view-article-comment/view-article-comment.component';
import { ComponentsModule } from "../../components/components.module";
import { ArticleCommentService } from '../../services/article-comment.service';
import {ArticleService} from "../../services/article.service";

const COMPONENTS = [
  ListArticleCommentComponent,
  AddArticleCommentComponent,
  EditArticleCommentComponent,
  FormArticleCommentComponent,
  ViewArticleCommentComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
ArticleCommentRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[ArticleCommentService,ArticleService]
})
export class ArticleCommentModule { }
