export class ResponseResult {
    result: string;
    message: string;
    data: object;


    constructor(result: string, message: string, data: Object) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
}
