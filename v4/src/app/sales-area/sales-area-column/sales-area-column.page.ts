import {Component, OnInit} from '@angular/core';
import {AreaColumnVo} from '../../../domain/vo/area-column-vo.model';
import {AreaColumnProvider} from '../../../services/sales-area-service/area-column';
import {PageQuery} from '../../../util/page-query.model';
import {ActivatedRoute} from '@angular/router';
import {NavController} from '@ionic/angular';

@Component({
    selector: 'app-sales-area-column',
    templateUrl: './sales-area-column.page.html',
    styleUrls: ['./sales-area-column.page.scss'],
})
export class SalesAreaColumnPage implements OnInit {

    areaColumn: AreaColumnVo;

    select: string = "1";

    isLoading:boolean = false;

    pageQuery: PageQuery = new PageQuery();

    constructor(public route: ActivatedRoute,public navCtrl: NavController,public areaColumnProvider: AreaColumnProvider) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.route.data.subscribe((data) => {
            this.areaColumn = data.data;
        });
    }

    goCommodity(commodityId) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodityId}]);
    }

}
