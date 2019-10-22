import { Injectable } from '@angular/core';
import {HttpServiceProvider} from '../http-service/http-service';
import {AppConfig} from '../../app/app.config';
import {HttpClient, HttpParams} from '@angular/common/http';
import {AlertController} from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class SearchService extends HttpServiceProvider {

  constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
    super(appConfig, http, alertCtrl, "/search");
  }

  keywordSearch(keyword,page,size) {
    const params = new HttpParams().set("keyword", keyword).set("page", page).set("size", size);
    return this.get("keywordSearch", params);
  }

  categorySearch(searchVo) {
    return this.postData("categorySearch",searchVo);
  }

}
