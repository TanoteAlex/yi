import {Component, OnInit} from '@angular/core';
import {SalesAreaVo} from '../../../domain/vo/sales-area-vo.model';
import {SalesAreaProvider} from '../../../services/sales-area-service/sales-area';
import {NavController} from '@ionic/angular';
import {NativeProvider} from '../../../services/native-service/native';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'sales-area-sales-area',
    templateUrl: './sales-area.page.html',
    styleUrls: ['./sales-area.page.scss'],
})
export class SalesAreaPage implements OnInit {

    salesArea: SalesAreaVo;

    slideOpts = {
        initialSlide: 0,
        slidesPerView: 'auto',
        spaceBetween: 7,
    };

    constructor(public salesAreaProvider:SalesAreaProvider,public nativeProvider: NativeProvider, public navCtrl: NavController,public route: ActivatedRoute,) {
    }

    ngOnInit() {


    }

    ionViewWillEnter(){
        this.route.data.subscribe((data) => {
            this.salesArea = data.data;
        });
    }


    getVip(commodity) {
        return parseInt(commodity.levelPrices[0].levelPrice, 10);
    }

    goCommodity(commodityId) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodityId}]);
    }

    goToAreaColumn(columnId){
        this.navCtrl.navigateForward(["AreaColumnPage", {id: columnId}]);
    }

}
