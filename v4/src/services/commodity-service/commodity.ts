import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";
import {AlertController} from "@ionic/angular";

/*
  Generated class for the CommodityProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class CommodityProvider extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "commodity");
    }

    getCommodity(commodityId) {
        const params = new HttpParams().set('commodityId', commodityId);
        return this.get("getCommodity", params);
    }

    getCommodityCategory() {
        const params = new HttpParams();
        return this.get('getCommodityCategory', params);
    }

    query(pageQuery) {
        return this.post('queryForApp', pageQuery);
    }

    homeQuery(pageQuery) {
        return this.post('queryCommoditiesForBottomLoad', pageQuery);
    }

    queryCommodityComment(pageQuery) {
        return this.post('queryComment', pageQuery);
    }

}
