import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'footer-tip',
    templateUrl: './footer-tip.component.html',
    styleUrls: ['./footer-tip.component.scss']
})
export class FooterTipComponent implements OnInit {
    @Input()
    tips: any = '';

    @Input()
    title: any = '温馨提示';

    constructor() {
    }

    ngOnInit() {
    }

}
