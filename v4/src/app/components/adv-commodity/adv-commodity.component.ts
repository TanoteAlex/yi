import {Component, Input, OnInit} from '@angular/core';
import {Commodity} from "../../../domain/original/commodity.model";
import {NavController} from "@ionic/angular";

@Component({
    selector: 'adv-commodity',
    templateUrl: './adv-commodity.component.html',
    styleUrls: ['./adv-commodity.component.scss']
})
export class AdvCommodityComponent implements OnInit {

    private _list;

    @Input()
    set list(list) {
        if (list && list[0] && list[0] instanceof Object && Array.isArray(list[0].recommendCommodities)){
            list[0].recommendCommodities = list[0].recommendCommodities.map( e => e.commodity)
            this._list = list[0];
        }
    }

    get list() {return this._list};

    constructor(public navCtrl: NavController) {

    }

    ngOnInit() {

    }

    goCommodity(id) {
        this.navCtrl.navigateForward(["CommodityPage", {id: id}])
    }

}
