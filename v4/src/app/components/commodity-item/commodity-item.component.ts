import {Component, Input} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'commodity-item',
    templateUrl: 'commodity-item.component.html',
    styleUrls: ['./commodity-item.component.scss']
})
export class CommodityItemComponent {
    @Input()
    commodity: any;

    @Input()
    isInvalid: boolean = false;

    constructor(public navCtrl: NavController) {
    }

    ngOnInit() {
    }

    detail() {
        this.navCtrl.navigateForward("CommodityPage", false, {queryParams: {id: this.commodity.id}});
    }

}
