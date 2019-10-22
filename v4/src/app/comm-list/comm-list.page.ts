import {Component} from '@angular/core';
import {ModalController, NavController} from '@ionic/angular';
import {Commodity} from '../../domain/original/commodity.model';
import {PageQuery} from '../../util/page-query.model';
import {NativeProvider} from '../../services/native-service/native';
import {ActivatedRoute} from '@angular/router';
import {ScreenPage} from '../screen/screen.page';
import {SearchService} from '../../services/search-service/search.service';
import {SearchVo} from '../../util/search-vo';
import {SUCCESS} from '../Constants';

const ARROW_ROUND_UP = 'arrow-round-up';
const ARROW_ROUND_DOWN = 'arrow-round-down';

@Component({
    selector: 'app-comm-list',
    templateUrl: './comm-list.page.html',
    styleUrls: ['./comm-list.page.scss'],
})
export class CommListPage {
    pageQuery: PageQuery = new PageQuery();
    list: Commodity[] = [];
    sort = 'hot';
    delaySearching = 0;

    isLoading: boolean = false;

    hotSegmentIcon = '';
    priceSegmentIcon = '';

    styleType: string = '1';

    searchKeyword;

    searchObj: SearchVo = new SearchVo();

    constructor(public router: ActivatedRoute, public nativeProvider: NativeProvider, public modalCtrl: ModalController,
                public navCtrl: NavController, public searchService: SearchService) {
    }

    ionViewWillEnter() {
        this.isLoading = true;
        this.searchObj.page = 0;
        this.searchObj.pageSize = this.pageQuery.requests.pageSize;
        this.searchObj.categoryId = this.router.params['value'].id;
        this.commoditiesFilter('hot');
    }

    getItems(event) {
        this.delaySearching += 1;
        setTimeout(() => {
            this.delaySearching -= 1;
            if (this.delaySearching == 0) {
                //执行后台请求的代码
                this.searchObj.keyword = event.target.value;
                this.getData();
            }
        }, 1000);
    }

    commoditiesFilter(type) {
        if (type == 'hot') {
            if (this.hotSegmentIcon == ARROW_ROUND_DOWN) {
                this.hotSegmentIcon = ARROW_ROUND_UP;
                this.searchObj.direction = 'ASC';
            } else {
                this.hotSegmentIcon = ARROW_ROUND_DOWN;
                this.searchObj.direction = 'DESC';
            }
            this.priceSegmentIcon = '';
            this.searchObj.sortBy = 'saleQuantity';

            this.searchObj.page = 0;
            this.searchObj.priceSection = null;
            this.getData();
        }
        if (type == 'price') {
            if (this.priceSegmentIcon == ARROW_ROUND_DOWN) {
                this.priceSegmentIcon = ARROW_ROUND_UP;
                this.searchObj.direction = 'DESC';
            } else {
                this.priceSegmentIcon = ARROW_ROUND_DOWN;
                this.searchObj.direction = 'ASC';
            }
            this.hotSegmentIcon = '';
            this.searchObj.sortBy = 'currentPrice';

            this.searchObj.page = 0;
            this.searchObj.priceSection = null;
            this.getData();
        }
    }

    private getData() {
        this.searchService.categorySearch(this.searchObj).then(response => {
            this.isLoading = false;
            if (response.result == SUCCESS) {
                const resultData = response.data;
                this.transform(resultData);
                if (resultData) {
                    this.pageQuery.covertResponses(resultData);
                    this.list = resultData.content;
                }
            }
        }, err => {
            this.isLoading = false;
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    async goScreen() {
        const modal1 = await this.modalCtrl.create({
            component: ScreenPage,
        });
        await modal1.present();

        await modal1.onDidDismiss().then(data => {
            //会添加两个价格 所以要删除两次
            if (data.data[0] != null) {
                if (data.data[1] === 1000) {
                    this.searchObj.priceSection = {startPrice: data.data[1], endPrice: 1000000};
                } else {
                    this.searchObj.priceSection = {startPrice: data.data[1], endPrice: data.data[0]};
                }

                this.searchObj.page = 0;
                this.searchObj.direction = null;
                this.searchObj.sortBy = null;
            }
            this.getData();
        });
    }

    goCommodity(commodity) {
        this.navCtrl.navigateForward(['CommodityPage', {id: commodity.id}]);
    }

    changStyle() {
        this.styleType = this.styleType == '1' ? '2' : '1';
    }

    doInfinite(event) {
        setTimeout(() => {
            this.isLoading = true;
            if (!this.pageQuery.isLast()) {
                this.pageQuery.plusPage();
                this.searchObj.page = this.pageQuery.requests.page - 1;
                this.searchObj.pageSize = this.pageQuery.requests.pageSize;
                this.searchService.categorySearch(this.searchObj).then(response => {
                    if (response.result == SUCCESS) {
                        const resultData = response.data;
                        this.transform(resultData);
                        if (resultData) {
                            this.pageQuery.covertResponses(resultData);
                            this.list = this.list.concat(resultData.content);
                        }
                    }
                    this.isLoading = false;
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    this.isLoading = false;
                });
            } else {
                this.isLoading = false;
            }
            event.target.complete();
        }, 500);
    }

    transform(data) {
        data.content = data.content.map(e => {
            return {
                id: e.id,
                imgPath: e.imgPath,
                productName: e.commodityName,
                commodityName: e.commodityName,
                productShortName: e.commodityShortName,
                discountInfo: e.discountInfo,
                originalPrice: e.originalPrice,
                currentPrice: e.currentPrice,
                levelPrices: e.levelPrices,
                returnVoucher: e.returnVoucher,
                freightSet: e.freightSet,
                unifiedFreight: e.unifiedFreight,
                commodityType: e.commodityType,
            };
        });
    }

    private getVip(commodity) {
        return parseInt(commodity.levelPrices[0].levelPrice, 10);
    }

}
