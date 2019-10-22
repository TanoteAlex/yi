import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AppConfig} from "../../app/app.config";
import {AlertController} from "@ionic/angular";
import {HttpServiceProvider} from "../http-service/http-service";

/*
  Generated class for the ArticleProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ArticleProvider extends HttpServiceProvider {

    constructor(public http: HttpClient, public appConfig: AppConfig, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "article");
    }

    getArticle(articleId) {
        const params = new HttpParams().set('articleId', articleId);
        return this.get("getArticle", params);
    }

    getArticleVo(page) {
        return this.post("query", page);
    }

    /*提交文章评论*/
    evaluationArticle(articleCommentBo) {
        return this.post("addArticleComment", articleCommentBo)
    }


}
