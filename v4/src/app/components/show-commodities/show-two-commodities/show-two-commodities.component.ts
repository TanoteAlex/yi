import {Component, Input, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-show-two-commodities',
    templateUrl: './show-two-commodities.component.html',
    styleUrls: ['./show-two-commodities.component.scss']
})
export class ShowTwoCommoditiesComponent implements OnInit {
    @Input()
    item;

    @Input()
    goCommodity

    constructor(public navCtrl: NavController,) {
    }

    ngOnInit() {
    }


}
