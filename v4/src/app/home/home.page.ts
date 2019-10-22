import {Component, ViewChild} from '@angular/core';
import {AlertController, NavController} from '@ionic/angular';
import {Banner} from '../../domain/original/banner.model';
import {BannerProvider} from '../../services/banner-service/banner';
import {NativeProvider} from '../../services/native-service/native';
import {SortPipe} from '../../pipes/sort/sort';
import {PageQuery} from '../../util/page-query.model';
import {Commodity} from '../../domain/original/commodity.model';
import {CommodityProvider} from '../../services/commodity-service/commodity';
import {MemberProvider} from '../../services/member-service/member';
import {hashChange} from '../../util/window-event';
import {GroupActiveService} from '../../services/group-active-service/group-active.service';
import {SUCCESS} from '../Constants';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss']
})
export class HomePage {

    banners: Banner[] = [];

    recommends: Array<any>;

    pageQuery: PageQuery = new PageQuery();

    constructor(public alertCtrl: AlertController, public nativeProvider: NativeProvider, public memberProvider: MemberProvider,
                public bannerProviders: BannerProvider, public navCtrl: NavController, public GroupActiveService: GroupActiveService,
                public commodityProvider: CommodityProvider) {

    }

    ngOnInit(){
        this.memberProvider.autoLogin(window.location.href);
        this.getData();
    }

    ionViewWillEnter() {

    }

    ionViewDidEnter() {
    }

    /**
     * 获取页面数据
     */
    private getData() {
        this.nativeProvider.showLoadingForI4().then(() => {
            this.bannerProviders.getBanner().then(data => {
                this.nativeProvider.hideLoadingForI4();
                this.banners = data.data;
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
                this.nativeProvider.hideLoadingForI4();
            });

            this.pageItems();
            this.getGroupListData();
        });
    }

    pageItems() {
        this.pageQuery.resetRequests();
        this.pageQuery.pageSize = 1;
        this.pageQuery.addOnlyFilter('position.positionType', '2', 'eq');
        this.bannerProviders.queryRecommends(this.pageQuery).then(
            response => {
                if (response.result == SUCCESS) {
                    this.recommends = response.data.content;
                    this.pageQuery.covertResponses(response.data);
                } else {
                    this.nativeProvider.showToastFormI4(response.message);
                }
            },
            err => this.nativeProvider.showToastFormI4('数据加载错误')
        );
    }



    /**
     * 下拉加载
     * @param data
     */
    doInfinite(event) {
        setTimeout(() => {


            // App logic to determine if all data is loaded
            if (!this.pageQuery.isLast()) {
                this.pageQuery.plusPage();
                this.bannerProviders.queryRecommends(this.pageQuery).then(
                    response => {
                        if (response.result == SUCCESS) {
                            this.recommends = this.recommends.concat(response.data.content);
                            this.pageQuery.covertResponses(response.data);
                        } else {
                            this.nativeProvider.showToastFormI4(response.message);
                        }
                        event.target.complete();
                    },
                    err => {
                        this.nativeProvider.showToastFormI4('数据加载错误');
                        event.target.complete();
                    },
                );
            }else{
                event.target.complete();
            }

            // and disable the infinite scroll
            if (event.length == 1000) {
                event.target.disabled = true;
            }
        }, 500);
    }

    goCommodity(id) {
        this.navCtrl.navigateForward(['CommodityPage', {id: id}]);
    }

    groupList;

    getGroupListData() {
        this.GroupActiveService.getLatestGroupBuyActivity().then(e => {
            if (e.result == SUCCESS) {
                this.groupList = e.data;
            }
        });
    }

    goGroupCommodity(activityProducts) {
        this.navCtrl.navigateForward(['CommodityGroupPage', {id: activityProducts.id}]);
    }
}
