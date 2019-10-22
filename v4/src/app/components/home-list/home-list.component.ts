import {Component, ComponentFactoryResolver, Input, OnInit, SimpleChanges, ViewChild, ViewContainerRef} from '@angular/core';

//todo 未完成
@Component({
    selector: 'app-home-list',
    templateUrl: './home-list.component.html',
    styleUrls: ['./home-list.component.scss']
})
export class HomeListComponent implements OnInit {

    @Input()
    banners = [];
    @Input()
    commodities = [];


    _controlRef;


    constructor(private componentFactoryResolver: ComponentFactoryResolver) {
    }

    ngOnInit() {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.banners.currentValue || changes.commodities.currentValue) {
            console.log("change");


        }
    }


}
