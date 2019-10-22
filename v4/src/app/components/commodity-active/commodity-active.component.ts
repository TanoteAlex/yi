import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'commodity-active',
    templateUrl: './commodity-active.component.html',
    styleUrls: ['./commodity-active.component.scss']
})
export class CommodityActiveComponent implements OnInit {
    @Input()
    commodity;

    constructor() {
    }

    ngOnInit() {
    }

}
