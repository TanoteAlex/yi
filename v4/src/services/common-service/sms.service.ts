import {Injectable} from '@angular/core';
import {AppConfig} from "../../app/app.config";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
    providedIn: 'root'
})
export class SmsService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "sms");
    }

    sendRegisterCode(phone) {
        const params = new HttpParams().set('phone', phone);
        return this.get("sendRegisterCode", params);
    }

    sendChangePwdCode(phone) {
        const params = new HttpParams().set('phone', phone);
        return this.get("sendChangePwdCode", params);
    }
}
