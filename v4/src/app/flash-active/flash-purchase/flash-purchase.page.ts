import { Component, OnInit } from '@angular/core';
import {NavController} from "@ionic/angular";
import {PageQuery} from "../../../util/page-query.model";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";

@Component({
  selector: 'app-flash-purchase',
  templateUrl: './flash-purchase.page.html',
  styleUrls: ['./flash-purchase.page.scss'],
})
export class FlashPurchasePage{

    state = "now";

    pageQuery: PageQuery = new PageQuery();

    list = [];

    constructor(public groupActiveProvider: GroupActiveService,public navCtrl :NavController) {
    }

    ionViewWillEnter() {
        this.listFilter("now")
    }

    listFilter(type) {
        this.queryFilter(type);
    }

    queryFilter(type) {
        switch (type) {
            case "now":
                this.groupActiveProvider.queryOrder(this.pageQuery.requests).then(e => {
                    this.list = e.content;
                });
                break;
            case "soon":
                break;
            case "tomorrow":
                break;
        }
    }

    goCommodity(commodity) {
        this.navCtrl.navigateForward(["CommodityFlashPage",{id: commodity.id}])
    }

}
