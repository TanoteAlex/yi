import {Injectable} from '@angular/core';
import {AppConfig} from "../../app/app.config";
import {HttpClient} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
    providedIn: 'root'
})
export class HomePageService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "memberMessage");
    }

    getQueryNotice(page) {
        return this.post("query", page);
    }
}
