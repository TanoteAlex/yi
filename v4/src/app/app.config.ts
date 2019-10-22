import {Injectable} from "@angular/core";

@Injectable()
export class AppConfig {

    baseURL: string = "http://127.0.0.1:8086/";   // 开发环境
    // baseURL: string = "http://h5server.my11mall.com/";      // 正式环境
    // baseURL: string = "http://test.h5server.my11mall.com/";      // 开发环境
    kuaidi100: string = "https://m.kuaidi100.com/";     // 开发环境

    wechatAppId = 'wx624636d7fc542eb3';

    /**
     *是否自动登录，用于微信公众号
     */
    // wechatAutoLogin: boolean = true;
    wechatAutoLogin:boolean=false;

    /**
     *是否app
     */
    // isApp:boolean=true;
    isApp: boolean = false;

    hostURL: string;
    imgBaseURL: string;

    //分享网址链接
    shareURL: string = this.getShareUrl();

    getShareUrl(): string {
        if (this.baseURL.indexOf('test') == -1) {
            return "http://h5.my11mall.com/";
        } else {
            return "http://test.h5.my11mall.com/";
        }
    }

    getBase(): string {
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
