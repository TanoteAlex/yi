import {Injectable} from '@angular/core';
import {AppConfig} from "../../app/app.config";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
    providedIn: 'root'
})
export class LocationService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "region");
    }

    getProvinceCity() {
        return this.get("getCommunityProvinceCity");
    }

    getProvince() {
        const params = new HttpParams().set('province', "");
        return this.get("getProvinces", params);
    }


}