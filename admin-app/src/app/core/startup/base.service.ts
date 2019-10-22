import {HttpClient, HttpHeaders, HttpParams,} from "@angular/common/http";
import {saveAs} from "file-saver";
import {HttpObserve} from "@angular/common/http/src/client";
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {Observable} from 'rxjs/internal/Observable';
import {ChooseQueryBase} from '../../routes/models/object-choose-query';
import {HttpGlobalHeader} from '@core/startup/http-global-header';

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

export abstract class BaseService {

  private httpGlobalHeader: HttpGlobalHeader;

  protected constructor(protected http: HttpClient, protected nameSpace: string) {
    // super(http, new DelonThemeConfig());
    this.httpGlobalHeader = HttpGlobalHeader.getInstance();
  }

  query(pageQuery: any): Observable<any> {
    return this.postData("query", pageQuery);
  }

  querySimple(pageQuery: any): Observable<any> {
    return this.postData("querySimple", pageQuery);
  }

  queryObjectsByConditions(chooseQuery: ChooseQueryBase): Observable<any> {
    return this.postData("queryObjectsByConditions", chooseQuery);
  }

  getAll(param?: any): Observable<any> {
    return this.postFormData("getAll", param);
  }

  getById(id: any): Observable<any> {
    return this.getByField("getById", "id", id);
  }

  getBoById(id: any): Observable<any> {
    return this.getByField("getBoById", "id", id);
  }

  getVoById(id: any): Observable<any> {
    return this.getByField("getById", "id", id);
  }

  getVoById1(id: any): Observable<any> {
    return this.getByField("getVoById", "id", id);
  }

  getSimpleById(id: any): Observable<any> {
    return this.getByField("getSimpleById", "id", id);
  }

  removeById(id: any): Observable<any> {
    return this.removeByField("removeById", "id", id);
  }

  add(record: any): Observable<any> {
    return this.postData("add", record);
  }

  update(record: any): Observable<any> {
    return this.postData("update", record);
  }

  updateState(param) {
    return this.postFormData("updateState", param);
  }

  updateShelf(param) {
    return this.postFormData("updateShelf", param);
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

    let url = `${this.nameSpace}/${func}`;

    return this.http.request("POST", url, addBody(this.getJsonOptions(), body));
  }

  postFormData(func: string, params: any): Observable<any> {
    let body = encodeFormData(params);

    let url = `${this.nameSpace}/${func}`;
    return this.http.request("POST", url, addBody(this.getFormOptions(), body));
  }

  getByField(func: string, paramName: string, paramValue: any): Observable<any> {
    let url = `${this.nameSpace}/${func}`;
    if (paramName != null) {
      if (url.indexOf("?") === -1) url += "?";
      url += encodeURIComponent(paramName) + "=" + encodeURIComponent(paramValue);
    }
    return this.http.request("GET", url, this.getFormOptions());
  }

  getByParams(func: string, params?: any): Observable<any> {
    let url = `${this.nameSpace}/${func}`;

    if (params != null) {
      if (url.indexOf("?") === -1) url += "?";
      url += encodeFormData(params);
    }
    return this.http.request("GET", url, this.getFormOptions());
  }

  removeByField(func: string, paramName: string, paramValue: any): Observable<any> {
    let url = `${this.nameSpace}/${func}`;

    if (paramName != null) {
      if (url.indexOf("?") === -1) url += "?";
      url += encodeURIComponent(paramName) + "=" + encodeURIComponent(paramValue);
    }
    return this.http.request("GET", url, this.getFormOptions());
  }

  removeByParams(func: string, params: any): Observable<any> {
    let url = `${this.nameSpace}/${func}`;

    if (params != null) {
      if (url.indexOf("?") === -1) url += "?";
      url += encodeFormData(params);
    }

    return this.http.request("GET", url, this.getFormOptions());
  }

  /**
   * 使用 GET / POST 方式下载附件, url 如果不以 "/" 开始则自动加上服务的基础路径
   *
   * @param {string} method
   * @param {string} filename
   * @param {string} url
   * @param params
   */
  download(method: string, filename: string, url: string, params: any) {
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
      if (!url.startsWith("/")) {
        url = this.nameSpace + "/" + url;
      }
    }

    let request: Observable<Blob>;
    if (method === "GET") {
      if (params != null) {
        if (url.indexOf("?") === -1) url += "?";
        url += encodeFormData(params);
      }

      request = this.http.request("GET", url, {
          headers: this.httpGlobalHeader.jsonHeaders,
          withCredentials: false,
          responseType: "blob"
        }
      );

    } else {
      let body;
      if (params) {
        if (params.toJsonString) {
          body = params.toJsonString();
        } else {
          body = JSON.stringify(params);
        }
      }

      request = this.http.request("GET", url, {
          body: body,
          headers: this.httpGlobalHeader.jsonHeaders,
          withCredentials: false,
          responseType: "blob"
        }
      );
    }

    request.subscribe(blob => {
      saveAs(blob, filename);
    });
  }

  protected getFormOptions() {
    return this.httpGlobalHeader.formOptions;
  }

  protected getJsonOptions() {
    return this.httpGlobalHeader.jsonOptions;
  }
}
