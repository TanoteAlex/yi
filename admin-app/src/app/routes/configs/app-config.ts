import {Injectable} from "@angular/core";

@Injectable()
export class AppConfig {

   private baseURL: string = "http://localhost:8084";   // 开发环境
//   private baseURL: string = "http://test.adminserver.my11mall.com";  // 测试环境
//   private baseURL: string = "http://adminserver.my11mall.com";   // 生产环境

    private hostURL: string;
    private imgBaseURL: string;

    constructor() {
        this.hostURL = this.baseURL + "";
        this.imgBaseURL = this.baseURL + "";
    }

    getBase() {
        return this.baseURL;
    }

    getAttachmentBase() {
        return this.baseURL + '/attachment/';
    }

    getHost() {
        return this.hostURL;
    }

    getImgBase() {
        return this.imgBaseURL;
    }

}
