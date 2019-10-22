import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
  providedIn: 'root'
})
export class RuleInfoService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "ruleInfo");
    }

    getQuestionType() {
        return this.get("getQuestionType");
    }

    getQuestion(questionTypeId) {
        const params = new HttpParams().set('id', questionTypeId);
        return this.get("getQuestion", params);
    }

    getBasicRule() {
        return this.get("getBasicRule");
    }

}
