import {HttpHeaders} from "@angular/common/http";

export class HttpGlobalHeader {
  private static instance: HttpGlobalHeader = new HttpGlobalHeader();

  jsonHeaders: HttpHeaders;
  formHeaders: HttpHeaders;

  jsonOptions: any;
  formOptions: any;

  constructor() {
    if (HttpGlobalHeader.instance) {
      throw new Error("错误: 请使用AppGlobal.getInstance() 代替使用new.");
    }

    HttpGlobalHeader.instance = this;

    this.jsonHeaders = new HttpHeaders().append('Content-Type', 'application/json');
    this.formHeaders = new HttpHeaders().append('Content-Type', 'application/x-www-form-urlencoded');

    this.initOptions();
  }

  /**
   * 获取当前实例
   *
   * @static
   * @returns {AppGlobal}
   */
  public static getInstance(): HttpGlobalHeader {
    return HttpGlobalHeader.instance;
  }

  private initOptions() {
    this.jsonOptions = {
      headers: this.jsonHeaders,
      withCredentials: true
    };

    this.formOptions = {
      headers: this.formHeaders,
      withCredentials: true
    }
  }
}
