import {Component, Input, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-commodity-list-first',
    templateUrl: './commodity-list-first.component.html',
    styleUrls: ['./commodity-list-first.component.scss']
})
export class CommodityListFirstComponent implements OnInit {

    @Input()
    commodityList;

    constructor(public navCtrl: NavController) {
    }

    ngOnInit() {
    }

    goCommodity(commodityId) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodityId}])
    }

    private labelContent(commodity) {
        if (commodity.returnVoucher){
            return `返${commodity.returnVoucher}元券`;
        } else if (commodity.freightSet==1 && commodity.unifiedFreight==0){
            return `包邮`;
        }else if (commodity.commodityType==1) {
            return `批发`;
        }
    }

    private getVip(commodity) {
        return commodity.levelPrices[0].levelPrice;
    }

}
