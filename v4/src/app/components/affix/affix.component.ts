import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-affix',
    templateUrl: './affix.component.html',
    styleUrls: ['./affix.component.scss']
})
export class AffixComponent implements OnInit {
    @Input()
    content

    constructor() {
    }

    ngOnInit() {
    }

    goTop() {
        this.content.scrollToTop(1000);
    }
}
