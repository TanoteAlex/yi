import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {ArticleRoutingModule} from './article-routing.module';
import {ListArticleComponent} from './list-article/list-article.component';
import {AddArticleComponent} from './add-article/add-article.component';
import {EditArticleComponent} from './edit-article/edit-article.component';
import {FormArticleComponent} from './form-article/form-article.component';
import {ViewArticleComponent} from './view-article/view-article.component';
import {ComponentsModule} from "../../components/components.module";
import {ArticleService} from "../../services/article.service";
import {CommodityService} from "../../services/commodity.service";


const COMPONENTS = [
  ListArticleComponent,
  AddArticleComponent,
  EditArticleComponent,
  FormArticleComponent,
  ViewArticleComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
ArticleRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[ArticleService,CommodityService]
})
export class ArticleModule { }
