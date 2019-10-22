import {HttpHeaders} from "@angular/common/http";
import { Injectable } from '@angular/core';

@Injectable()
export class HttpGlobalHeader {
    private static instance: HttpGlobalHeader = new HttpGlobalHeader();
    private static token: string;

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
      let lToken = localStorage.getItem("supplier-token");
      if(lToken){
        this.jsonHeaders = this.jsonHeaders.append("supplier-token", lToken);
        this.formHeaders = this.formHeaders.append("supplier-token", lToken);
      }
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

    /**
     * 获取当前用户登录后的JWT令牌
     * @returns {string}
     */
    public static getToken(): string {
        return HttpGlobalHeader.token;
    }

    /**
     * 设置当前用户登录后的JWT令牌
     * @param {string} TOKEN
     */
    public static setToken(TOKEN: string): void {

        HttpGlobalHeader.token = TOKEN;

        let instance = this.getInstance();
      instance.setToken(TOKEN);

    }

    /**
     * 设置当前用户登录后的JWT令牌
     * @param {string} TOKEN
     */
    private setToken(TOKEN: string): void {
        if (TOKEN == null || TOKEN.trim().length === 0) {
            this.jsonHeaders = this.jsonHeaders.delete("supplier-token");
            this.formHeaders = this.formHeaders.delete("supplier-token");

        } else {
          let lToken = localStorage.getItem("supplier-token");
          if(lToken){
            this.jsonHeaders = this.jsonHeaders.set("supplier-token", lToken);
            this.formHeaders = this.formHeaders.set("supplier-token", lToken);
          }else {
            this.jsonHeaders = this.jsonHeaders.set("supplier-token", TOKEN);
            this.formHeaders = this.formHeaders.set("supplier-token", TOKEN);
          }
        }

        this.initOptions();
    }

    private initOptions() {
        this.jsonOptions = {
            headers: this.jsonHeaders,
            withCredentials: false
        };

        this.formOptions = {
            headers: this.formHeaders,
            withCredentials: false
        }
    }
}
