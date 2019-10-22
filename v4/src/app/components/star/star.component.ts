import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
    selector: 'star',
    templateUrl: './star.component.html',
    styleUrls: ['./star.component.scss']
})
export class StarComponent {

    @Input()
    num: number = 0;

    @Input()
    enable: boolean = true;

    @Output()
    result = new EventEmitter();

    noSelect = "assets/app_icon/basic/星评（未选中）@2x.png";
    select = "assets/app_icon/basic/星评（选中）@2x.png";

    stars = [
        {img: this.noSelect},
        {img: this.noSelect},
        {img: this.noSelect},
        {img: this.noSelect},
        {img: this.noSelect}
    ];

    constructor() {

    }

    ngOnInit() {
        this.star(this.num - 1);
    }

    selectStar(i) {
        if (this.enable) {
            this.star(i);
            this.result.emit(i + 1);
        }
    }

    star(i) {
        this.stars.forEach((e, index) =>
            e.img = this.noSelect
        );
        this.stars.forEach((e, index) => {
            if (index <= i) {
                e.img = this.select
            }
        })
    }

}
