import {Component, Input, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {isArray} from "rxjs/internal-compatibility";
import {AdvertisementBo} from "../../../../domain/bo/advertisement-bo.model";

@Component({
    selector: 'app-show-three-commodities',
    templateUrl: './show-three-commodities.component.html',
    styleUrls: ['./show-three-commodities.component.scss']
})
export class ShowThreeCommoditiesComponent implements OnInit {
    _item = [];

    @Input()
    set item(item:AdvertisementBo){
        if(item && isArray(item.commodities)){
            while (item.commodities.length>3){
                this._item.push(item.commodities.splice(0,3))
            }
        }
    }

    @Input()
    goCommodity;
    constructor(public navCtrl: NavController,) {
        console.log(this.item);
    }

    ngOnInit() {
    }

    private getVip(commodity) {
        return parseInt(commodity.levelPrices[0].levelPrice, 10);
    }


}
