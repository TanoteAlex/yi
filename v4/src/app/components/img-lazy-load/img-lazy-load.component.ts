import {Component, Input, OnInit} from '@angular/core';
import {ObjectUtils} from '../../../util/object-utils';

@Component({
    selector: 'img-lazy-load',
    templateUrl: './img-lazy-load.component.html',
    styleUrls: ['./img-lazy-load.component.scss']
})
export class ImgLazyLoadComponent {
    default: string = 'assets/loading.gif';
    @Input() src: string;

    constructor() {
    }

    ngOnChanges() {
        let img = new Image();
        img.src = this.src;
        img.onload = () => {
            this.default = this.src;
        };
        img.onerror = () => {
            this.default = 'assets/imgs/null-category.png';
        }
    }

}
