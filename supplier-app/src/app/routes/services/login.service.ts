import {Injectable} from '@angular/core';
import { BaseService } from './base.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {environment} from "@env/environment";
import { _HttpClient } from '@delon/theme';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { HttpObserve } from '@angular/common/http/src/client';
import { Observable } from 'rxjs/internal/Observable';
import { HttpGlobalHeader } from '../configs/http-global-header';

const encodeFormData = ObjectUtils.encodeFormData;

/**
 * Construct an instance of `HttpRequestOptions<T>` from a source `HttpMethodOptions` and
 * the given `body`. Basically, this clones the object and adds the body.
 */
function addBody<T>(
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

@Injectable()
export class LoginService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);


  private httpGlobalHeader: HttpGlobalHeader;

    constructor(public http: _HttpClient) {
        // super(http, "auth");
      this.httpGlobalHeader = HttpGlobalHeader.getInstance();
    }

    loginForSupplier(username, password): any {
        return this.postFormData('/auth/loginForSupplier',{
          username: username,
          password: password,
        })
    }

    getBasicInfo(){
      return
    }

  postData(func: string, param: any): Observable<any> {
    let body;
    if (param) {
      if (param.toJsonString) {
        body = param.toJsonString();
      } else {
        body = JSON.stringify(param);
      }
    }

    let url = `${environment.SERVER_URL}/${func}`;

    return this.http.request("POST", url, addBody(this.getJsonOptions(), body));
  }

  postFormData(func: string, params: any): Observable<any> {
    let body = encodeFormData(params);

    let url = `${environment.SERVER_URL}/${func}`;
    return this.http.request("POST", url, addBody(this.getFormOptions(), body));
  }

  protected getFormOptions() {
    return this.httpGlobalHeader.formOptions;
  }

  protected getJsonOptions() {
    return this.httpGlobalHeader.jsonOptions;
  }

}
