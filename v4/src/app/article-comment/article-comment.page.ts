import {Component} from '@angular/core';
import {Article} from "../../domain/original/article.model";
import {ArticleProvider} from "../../services/article-service/article";
import {NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {ActivatedRoute} from "@angular/router";
import {scrollToZero} from "../../util/bug-util";

@Component({
    selector: 'app-article-comment',
    templateUrl: './article-comment.page.html',
    styleUrls: ['./article-comment.page.scss'],
})
export class ArticleCommentPage {

    article: Article;
    content = "";

    constructor(public route: ActivatedRoute, public articleProvider: ArticleProvider, public navCtrl: NavController, public nativeProvider: NativeProvider) {

    }

    ionViewWillEnter() {
        this.articleProvider.getArticle(this.route.params["value"].articleId).then(data => {
            this.article = data.data
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    comment() {
        if (this.content != "") {
            let articleCommentBo = {
                commentator: MemberProvider.getLoginMember().nickname,
                article: {id:this.route.params["value"].articleId},
                commentContent: this.content
            };
            this.articleProvider.evaluationArticle(articleCommentBo).then(e => {
                if (e.result == "SUCCESS") {
                    this.nativeProvider.showToastFormI4("评论成功", ()=>{
                        this.navCtrl.goBack();
                    });
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
            });
        } else {
            this.nativeProvider.showToastFormI4("请填写评论内容");
        }
    }

    blur(){
        scrollToZero();
    }

}
