// import "rxjs/add/operator/toPromise";
// import "rxjs/add/operator/timeout";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AppConfig} from "../../app/app.config";
// import {NativeProvider} from "../native-service/native";
import {AlertController} from "@ionic/angular";
import {delay, retryWhen, timeout} from "rxjs/operators";
import {SUCCESS} from "../../app/Constants";
import {HttpObserve} from '@angular/common/http/src/client';

/*
  Generated class for the HttpServiceProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
const TIMEOUT = 30000
// const TIMEOUT = 1000
const DELAY_TIME = 20000

/**
 * 通用请求错误
 * @param err
 */
export function customOnError(err) {
    this.nativeProvider.showToastFormI4(err.message);
}

/**
 * 通用返回结果处理
 * @param e
 * @param func
 */
export function customOnResult(e, func) {
    if (e.result == SUCCESS) {
        func();
    } else {
        this.nativeProvider.showToastFormI4(e.message);
    }
}

export function addBody<T>(
    options: {
        headers?: HttpHeaders | { [header: string]: string | string[] },
        observe?: HttpObserve,
        params?: HttpParams | { [param: string]: string | string[] },
        reportProgress?: boolean,
        responseType?: 'arraybuffer' | 'blob' | 'json' | 'text',
        withCredentials?: boolean,
    },
    body: T | null): any {
    if (body == null) return options;
    return {
        body,
        headers: options.headers,
        observe: options.observe,
        params: options.params,
        reportProgress: options.reportProgress,
        responseType: options.responseType,
        withCredentials: options.withCredentials,
    };
}


export class HttpServiceProvider {
    nameSpace: string = ''

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController, public nameSpace1: string) {
        this.nameSpace = nameSpace1
    }

    set setNameSpace(nameSpace) {
        this.nameSpace = nameSpace;
    }

    get getNameSpace() {
        return this.nameSpace;
    }

    handleError(e) {
        if (e.name == 'TimeoutError') {
            console.log("请求超时")
            throw new Error("请求超时")
        } else {
            console.log("服务器异常" + e.name)
            throw new Error("服务器异常")
        }
    }

    /**
     * 通用GET请求
     * @param {string} url
     * @param paramMap
     * @returns {Promise<any>}
     */
    protected get(url: string, paramMap: HttpParams = null): Promise<any> {
        const options = {
            params: paramMap,
            // withCredentials: true,
            headers: new HttpHeaders({
                "Content-Type": "application/json; charset=UTF-8",
                "Access-Control-Allow-Origin": '*',
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept, Authorization",
                "Access-Control-Max-Age": "3600",
                "Access-Control-Allow-Methods": "POST, GET, OPTIONS, DELETE"
            }),
        };
        return this.http.get(this.appConfig.baseURL + this.nameSpace + "/" + url, options).pipe(timeout(TIMEOUT)).toPromise().catch(this.handleError);
    }

    /**
     * 以json形式post提交
     * @param {string} url
     * @param body
     * @param {boolean} showLoading
     * @returns {Promise<any>}
     */
    protected post(url: string, body: any = null, showLoading?: boolean): Promise<any> {
        const options = {
            // withCredentials: true,
            headers: new HttpHeaders({
                'Content-Type': 'application/json; charset=UTF-8',
                'loading': showLoading ? "true" : "false"
            }),
        };
        return this.http.post(this.appConfig.baseURL + this.nameSpace + "/" + url, body, options).pipe(timeout(TIMEOUT)).toPromise().catch(this.handleError);
    }

    postData(func: string, param: any): Promise<any> {
        let body;
        if (param) {
            if (param.toJsonString) {
                body = param.toJsonString();
            } else {
                body = JSON.stringify(param);
            }
        }

        const options = {
            // withCredentials: true,
            headers: new HttpHeaders({
                'Content-Type': 'application/json; charset=UTF-8'
            }),
        };

        let url = `${this.appConfig.getBase()}/${this.nameSpace}/${func}`;
        return this.http.post(url, body, options).pipe(timeout(TIMEOUT)).toPromise().catch(this.handleError);
    }

    /**
     * 以表单方式提交
     * @param {string} url
     * @param paramMap
     * @param {boolean} showLoading
     * @returns {Promise<any>}
     */
    protected postForm(url: string, body: any = null, showLoading?: boolean): Promise<any> {
        const options = {
            // withCredentials: true,
            headers: new HttpHeaders({
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'loading': showLoading ? "true" : "false"
            }),
        };
        return this.http.post(this.appConfig.baseURL + url, body, options).toPromise();
    }


    protected postBodyAndParams(url: string, body: any = null, paramMap: HttpParams = null, showLoading?: boolean): Promise<any> {
        const options = {
            params: paramMap,
            withCredentials: true,
            headers: new HttpHeaders({
                'Content-Type': 'application/json; charset=UTF-8',
                'loading': showLoading ? "true" : "false"
            }),
        };
        return this.http.post(this.appConfig.getBase + url, body, options).toPromise();
    }

    protected put(url: string, body: any = null): Promise<any> {
        const options = {
            withCredentials: true,
        };
        return this.http.put(this.appConfig.getBase + url, body, options).toPromise();
    }

    protected delete(url: string, paramMap: HttpParams = null): Promise<any> {
        const options = {
            params: paramMap,
            withCredentials: true,
        };
        return this.http.delete(this.appConfig.getBase + url, options).toPromise();
    }

    protected patch(url: string, body: any = null): Promise<any> {
        const options = {
            withCredentials: true,
        };
        return this.http.patch(this.appConfig.getBase + url, body, options).toPromise();
    }

    /**
     * 将对象转为查询参数
     * @param paramMap
     * @returns {HttpParams}
     */
    protected buildHttpParams(paramMap: any): HttpParams {
        let params = new HttpParams();
        if (!paramMap) {
            return params;
        }
        for (let key in paramMap) {
            let val = paramMap[key];
            // if (val instanceof Date) {
            //     val = Utils.dateFormat(val, 'yyyy-MM-dd hh:mm:ss')
            // }
            params = params.set(key, val);
        }
        return params;
    }


    /**
     * url中如果有双斜杠替换为单斜杠
     * 如:http://88.128.18.144:8080//api//demo.替换后http://88.128.18.144:8080/api/demo
     * @param url
     * @returns {string}
     */
    private formatUrl(url: any): string {
        if (url.indexOf('http://') == -1 && url.indexOf('https://') == -1) {
            url = this.appConfig.getBase + url;
        }
        let index = url.indexOf('//') + 2;
        return url.substring(0, index) + url.substring(index).replace(/\/\//g, '/');
    }

    protected getFormOptions() {
        return new HttpHeaders().append('Content-Type', 'application/x-www-form-urlencoded');
    }

    protected getJsonOptions() {
        return new HttpHeaders().append('Content-Type', 'application/json');
    }

}
