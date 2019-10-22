import {Component, Input, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-show-four-commodities',
    templateUrl: './show-four-commodities.component.html',
    styleUrls: ['./show-four-commodities.component.scss']
})
export class ShowFourCommoditiesComponent implements OnInit {
    @Input()
    item;
    @Input()
    goCommodity

    constructor(public navCtrl: NavController,) {
    }

    ngOnInit() {
    }


}
