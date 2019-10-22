import {Pipe, PipeTransform} from '@angular/core';

/**
 * Generated class for the FormErrorPipe pipe.
 *
 * See https://angular.io/api/core/Pipe for more info on Angular Pipes.
 */
@Pipe({
    name: 'formError',
})
export class FormErrorPipe implements PipeTransform {
    /**
     * Takes a value and makes it lowercase.
     */
    transform(value: any, ...args) {
        console.log(value);
        let errorsMsg = "";
        for (let errorsKey in value) {
            if (errorsKey == "required") {
                errorsMsg += "该项必填 "
            }
            if (errorsKey == "maxlength") {
                errorsMsg += "超过最大长度 "
            }
            if (errorsKey == "minlength") {
                errorsMsg += "超过最小长度 "
            }
            if (errorsKey == "pattern") {
                errorsMsg += "数据格式错误 "
            }
        }
        return errorsMsg;
    }
}
