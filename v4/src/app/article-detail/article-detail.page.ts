import {Component} from '@angular/core';
import {Article} from "../../domain/original/article.model";
import {PageQuery} from "../../util/page-query.model";
import {ArticleProvider} from "../../services/article-service/article";
import {ActivatedRoute} from "@angular/router";
import {ModalController, NavController} from "@ionic/angular";
import {DomSanitizer} from "@angular/platform-browser";
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {ShareModalPage} from "../share-modal/share-modal.page";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {ShareClickModalPage} from "../share-modal/share-click-modal/share-click-modal.page";
import {WechatforAppService} from "../../services/forApp-service/wechat.service";
import {AppConfig} from "../app.config";

@Component({
    selector: 'app-article-detail',
    templateUrl: './article-detail.page.html',
    styleUrls: ['./article-detail.page.scss'],
})
export class ArticleDetailPage {

    article: Article;
    articleVo: Article[] = [];

    pageQuery: PageQuery = new PageQuery();

    articleEvaluation;

    isShowAllEvaluation: boolean = false;

    /**
     * 上级会员id
     * @type {number}
     */
    preMemberId = '';

    constructor(public nativeProvider: NativeProvider, public domSanitizer: DomSanitizer, public navCtrl: NavController, public articleProvider: ArticleProvider,
                public route: ActivatedRoute, public wechatProvider: WechatService, public modalCtrl: ModalController,public memberProvider: MemberProvider,
                public appConfig: AppConfig, public wechatforAppService: WechatforAppService) {

    }

    ionViewWillEnter() {
        let href = window.location.href;
        if (href.indexOf('preMemberId') != -1){
            this.preMemberId = href.split('?')[0].split('preMemberId=')[1];

            this.memberProvider.initLoginSession();
            this.memberProvider.autoLogin(href);
        }

        this.route.data.subscribe((data) => {
            this.article = data.data;
            this.article.articleComments.sort((e1, e2) => e1.commentTime > e2.commentTime ? -1 : 1);
            this.articleEvaluation = this.article.articleComments.slice(0, 2);

            this.article.content=this.article.content.replace('<p','<p style="font-size:0!important" ');
            this.article.content = this.domSanitizer.bypassSecurityTrustHtml(this.article.content);
            for (let i = 0; i < this.article.commodities.length; i++) {
                this.article.articleCommodities = data.data.commodities.map(e => {
                    return {
                        id: e.id,
                        imgPath: e.imgPath,
                        productName: e.commodityName,
                        productShortName: e.commodityShortName,
                        discountInfo: e.discountInfo,
                        currentPrice: e.currentPrice,
                        originalPrice: e.originalPrice,
                        levelPrices: e.levelPrices,
                        returnVoucher: e.returnVoucher,
                        freightSet: e.freightSet,
                        unifiedFreight: e.unifiedFreight,
                        commodityType: e.commodityType,
                    }
                });
            }
        });

        this.articleProvider.getArticleVo(this.pageQuery).then(data => {
            this.pageQuery.covertResponses(data);
            this.articleVo = this.getOtherArticles(data.content).slice(0, 2);
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });

        this.initShareData(false);
    }

    goCommodity(commodity) {
        this.navCtrl.navigateForward(["CommodityPage", {"id": commodity.id}]);
    }

    goWriteComment() {
        if(MemberProvider.isLogin()){
            this.navCtrl.navigateForward(["ArticleCommentPage", {"articleId": this.route.params["value"].articleId}]);
        }else {
            this.navCtrl.navigateForward("LoginPage");
        }
    }

    goDetail(articleId) {
        this.navCtrl.navigateForward(["ArticleDetailPage", {"articleId": articleId}])
    }

    allEvaluation() {
        this.articleEvaluation = this.article.articleComments;
        this.isShowAllEvaluation = true;
    }

    closeEvaluation() {
        this.articleEvaluation = this.article.articleComments.slice(0, 2);
        this.isShowAllEvaluation = false;
    }


    async openShareModal() {
        this.initShareData();
        /*const modal = await this.modalCtrl.create({
                component: ShareClickModalPage,
            }
        );
        await modal.present();*/
    }

    /*底部其它文章的推荐，需要先筛除本篇文章*/
    getOtherArticles(articleList) {
        return articleList.filter( e => e.id != this.route.params["value"].articleId);
    }

    initShareData(isShowModal = true) {
        let memberId;
        if(MemberProvider.isLogin()){
            memberId = MemberProvider.getLoginMember().id;
        }
        let shareData = {
            title: this.article.title,
            desc: this.article.subtitle,
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?ArticleDetailPage&articleId='+this.article.id + "&preMemberId=" + memberId + '?'),
            imgUrl: this.article.imgPath,
        };
        /**
         * 平台
         */
        this.wechatforAppService.shareDate(shareData, isShowModal)
    }

    private getVip(commodity) {
        return parseInt(commodity.levelPrices[0].levelPrice, 10);
    }
}
