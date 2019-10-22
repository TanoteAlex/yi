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
export class InviteActivityProvider extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "inviteActivity");
    }

    getLatestInviteActivity(memberId) {
        const params = new HttpParams().set('memberId', memberId);
        return this.get("getLatestInviteActivity", params);
    }


}
