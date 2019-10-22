import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {ComponentsModule} from "../components/components.module";

import {IonicModule} from '@ionic/angular';

import {ArticleDetailPage} from './article-detail.page';
import {PipesModule} from "../../pipes/pipes.module";
import {ArticleDetailResolverService} from "./article-detail-resolver.serivce";

const routes: Routes = [
    {
        path: '',
        component: ArticleDetailPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        PipesModule,
        RouterModule.forChild([{path: '', component: ArticleDetailPage, resolve: {
                data: ArticleDetailResolverService
            }}])
    ],
    declarations: [ArticleDetailPage]
})
export class ArticleDetailPageModule {
}
