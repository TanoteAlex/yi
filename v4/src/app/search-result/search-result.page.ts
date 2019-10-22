import {Component, ViewChild} from '@angular/core';
import {Commodity} from '../../domain/original/commodity.model';
import {PageQuery} from '../../util/page-query.model';
import {IonSearchbar, NavController} from '@ionic/angular';
import {CommodityProvider} from '../../services/commodity-service/commodity';
import {NativeProvider} from '../../services/native-service/native';
import {Storage} from '@capacitor/core';
import {MemberProvider} from '../../services/member-service/member';
import {SearchService} from '../../services/search-service/search.service';
import {SUCCESS} from '../Constants';

const HISTORY_SEARCH = 'HISTORY_SEARCH';

@Component({
    selector: 'app-search-result',
    templateUrl: './search-result.page.html',
    styleUrls: ['./search-result.page.scss'],
})
export class SearchResultPage {

    list: Commodity[] = [];

    pageQuery: PageQuery = new PageQuery();

    delaySearching = 0;

    historySearch: string[] = [];

    searchItem: string;

    tips: string;

    isSearching: boolean = false;

    @ViewChild('searchbar') searchbar: IonSearchbar;

    constructor(public nativeProvider: NativeProvider,
                public navCtrl: NavController, public memberProvider: MemberProvider, public searchService: SearchService) {
        this.loadHistory();
    }

    ionViewWillEnter() {
        this.searchbar.setFocus();
    }

    getItems(event) {
        this.searchResult(event.target.value);
    }

    private searchResult(value) {
        this.tips = '';
        if (value == '') {
            this.list = [];
            return;
        }

        this.delaySearching += 1;
        setTimeout(() => {
            this.delaySearching -= 1;
            if (this.delaySearching == 0) {
                //执行后台请求的代码
                this.pageQuery.resetRequests();
                this.getEsData(value, 0, 10);
                this.saveHistory(value);
            }
        }, 1000);
    }

    private getEsData(keyword, page, size) {
        this.isSearching = true;
        this.searchService.keywordSearch(keyword, page, size).then(response => {
            if (response.result == SUCCESS) {
                const resultData = response.data;
                console.log("resultData="+JSON.stringify(resultData))
                this.transform(resultData);
                if (resultData) {
                    this.pageQuery.covertResponses(resultData);
                    this.list = resultData.content;
                } else {
                    this.tips = '无相关商品~';
                }
            }
            this.isSearching = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isSearching = false;
        });
    }

    async homeBack() {
        this.navCtrl.goBack(false);
    }

    detail(commodity) {
        this.navCtrl.navigateForward(['CommodityPage', {id: commodity.id}]);
    }

    setKey(value) {
        this.searchItem = value;
        this.searchResult(value);
    }

    /**
     * 搜索历史 缓存部分
     */
    saveHistory(value) {
        if (value == '') {
            return;
        }
        if (this.historySearch.some((e => {
            return e == value;
        }))) {
            return;
        }
        if (this.historySearch.length >= 5) {
            this.historySearch.pop();
        }
        this.historySearch.unshift(value);
        Storage.set({key: HISTORY_SEARCH, value: JSON.stringify(this.historySearch)});
    }

    async loadHistory() {
        let historyItem = await Storage.get({key: HISTORY_SEARCH});
        if (!historyItem.value) {
            this.historySearch = [];
        } else {
            this.historySearch = JSON.parse(historyItem.value);
        }
    }

    removeHistory() {
        this.historySearch = [];
        Storage.remove({key: HISTORY_SEARCH});
    }

    doInfinite(event) {
        setTimeout(() => {
            this.isSearching = true;
            if (!this.pageQuery.isLast()) {
                this.pageQuery.plusPage();
                this.searchService.keywordSearch(this.searchItem, this.pageQuery.requests.page-1, this.pageQuery.requests.pageSize).then(response => {
                    if (response.result == SUCCESS) {
                        const resultData = response.data;
                        this.transform(resultData);
                        if (resultData) {
                            this.pageQuery.covertResponses(resultData);
                            this.list = this.list.concat(resultData.content);
                        } else {
                            this.tips = '无相关商品~';
                        }
                    }
                    this.isSearching = false;
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    this.isSearching = false;
                });
            } else {
                this.isSearching = false;
                this.tips = '到底咯~';
            }
            event.target.complete()
        }, 500);
    }

    transform(data) {
        data.content = data.content.map(e => {
            return {
                id: e.commodityId,
                productName: e.commodityName,
                imgPath: e.imgPath,
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
