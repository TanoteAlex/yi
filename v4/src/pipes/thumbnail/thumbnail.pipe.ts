import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'thumbnailPipe'
})
export class ThumbnailPipe implements PipeTransform {

    /**
     *
     * @param value
     * @param {string} size  图片规格大小
     * @returns {any}
     */
    transform(value: any, size: string = "normal"): any {
        if (!value) {
            return 'assets/imgs/null-category.png';
        }
        let postfix = "";
        switch (size) {
            case "normal":
                return value;
            case "s":
                return value + "_300_300_s" + postfix;
            case "m":
                return value + "_750_650_m" + postfix;
            case "l":
                return value + "_800_800_l" + postfix;
            default:
                return value;
        }
    }
}
