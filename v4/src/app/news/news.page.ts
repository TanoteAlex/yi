import {Component, OnInit} from '@angular/core';
import {NativeProvider} from "../../services/native-service/native";
import {AlertController, ModalController, NavController} from "@ionic/angular";
import {NoticeVo} from "../../domain/vo/notice-vo";
import {PageQuery} from "../../util/page-query.model";
import {MemberProvider} from "../../services/member-service/member";
import {HomePageService} from "../../services/banner-service/home-page.service";
import {SignSuccessModalPage} from "../sign-in/sign-success-modal/sign-success-modal.page";
import {RewardAlertModalPage} from "../rule-info/reward-alert-modal/reward-alert-modal.page";

@Component({
    selector: 'app-news',
    templateUrl: './news.page.html',
    styleUrls: ['./news.page.scss'],
})
export class NewsPage implements OnInit {

    list: NoticeVo[] = [];

    pageQuery: PageQuery = new PageQuery();

    constructor(public nativeProvider: NativeProvider, public homePageProvider: HomePageService, public navCtrl: NavController, public alertCtrl: AlertController,
                public modalCtrl: ModalController,) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        if (MemberProvider.isLogin()) {
            this.pageQuery.addFilter('member.id', MemberProvider.getLoginMember().id,'eq');
        }
        this.getData(this.pageQuery);
    }

    private getData(page: PageQuery) {
        this.homePageProvider.getQueryNotice(page.requests).then(e => {
            this.list = e.content;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    doRefresh(refresher) {
        this.homePageProvider.getQueryNotice(this.pageQuery.requests).then(e => {
            this.pageQuery.covertResponses(e);
            this.list = e.content;
            refresher.target.complete();
        }, err => refresher.target.complete())
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.homePageProvider.getQueryNotice(this.pageQuery.requests).then(e => {
                    this.list = this.list.concat(e.content);
                    this.pageQuery.covertResponses(e);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

    test() {
        // this.paramsModal();
    }

    //提示相应的分步发放的代金券
    async paramsModal() {
        const modal = await this.modalCtrl.create({
                component: RewardAlertModalPage
            }
        );
        await modal.present();
    }


}
