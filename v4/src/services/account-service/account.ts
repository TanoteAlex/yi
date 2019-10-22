import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";

/*
  Generated class for the AccountProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class AccountProvider extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "member");
    }

    getMember(accountId) {//无用？11.30
        const params = new HttpParams().set('memberId', accountId);
        return this.get("getMember", params);
    }

}
