import {Pipe, PipeTransform} from '@angular/core';
import {ADDRESS} from "../../app/Constants";

/**
 * Generated class for the DistrictPipe pipe.
 *
 * See https://angular.io/api/core/Pipe for more info on Angular Pipes.
 */
@Pipe({
    name: 'districtPipe',
})
export class DistrictPipe implements PipeTransform {
    /**
     * Takes a value and makes it lowercase.
     */
    transform(value: string, ...args) {
        for (let province in this.address) {
            if (province == value) {
                return this.address[province].name;
            }
            for (let city in this.address[province].child) {
                if (city == value) {
                    return this.address[province].child[city].name;
                }
                for (let district in this.address[province].child[city].child) {
                    if (district == value) {
                        return this.address[province].child[city].child[district]
                    }
                }
            }
        }
    }


    address = ADDRESS;

}
