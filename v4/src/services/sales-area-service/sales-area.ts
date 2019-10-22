import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AlertController} from "@ionic/angular";
import {HttpServiceProvider} from "../http-service/http-service";
import {AppConfig} from "../../app/app.config";

@Injectable()
export class SalesAreaProvider extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "salesArea");
    }

    getSalesAreaById(id) {
        const params = new HttpParams().set('id', id);
        return this.get("getVoById", params);
    }


}
