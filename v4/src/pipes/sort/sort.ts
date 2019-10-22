import {Pipe, PipeTransform} from '@angular/core';

/**
 * Generated class for the SortPipe pipe.
 *
 * See https://angular.io/api/core/Pipe for more info on Angular Pipes.
 */
@Pipe({
    name: 'sortPipe',
})
export class SortPipe implements PipeTransform {
    /**
     *
     * @param {any[]} values    排序数组
     * @param sortFiled         排序字段    默认id
     * @param order             true升序 false降序 默认false
     * @returns {any[]}
     */
    transform(values: any[], sortFiled: string = "id", order: boolean = false) {
        if (values) {
            values.sort((e1, e2) => {
                if (e1[sortFiled] > e2[sortFiled]) {
                    return !order ? 1 : -1
                } else {
                    return order ? 1 : -1
                }
            });
        }
        return values;
    }
}
