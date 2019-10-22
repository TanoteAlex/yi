import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AlertController} from "@ionic/angular";
import {HttpServiceProvider} from "../http-service/http-service";
import {AppConfig} from "../../app/app.config";

/*
  Generated class for the BannerProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class BannerProvider extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "cms");
    }

    /**
     * 获取首页所有数据
     */
    getHomePageData() {
        return this.getBanner().then(data => {
           return this.getHomeData().then(data1 => {
                return {banner: data, homeData: data1}
            })
        });
    }

    getBanner() {
        return this.get("getBanner");
    }

    getHomeData() {
        const params = new HttpParams().set('positionType', '2');
        return this.get("getRecommends", params);
    }

    getRecommends() {
        const params = new HttpParams().set('positionType', '3');
        return this.get("getRecommends", params);
    }

    queryRecommends(pageQuery) {
        return this.postData('queryRecommends', pageQuery);
    }

    /**
     *
     * @param position
     * @returns {Promise<any>}
     */
    getAdvertisingCommodity(position) {
        const params = new HttpParams().set('positionType', position);
        return this.get("getRecommends", params);
    }


}
