import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
    providedIn: 'root'
})
export class GroupActiveService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "groupBuyActivity");
    }

    queryOrder(page) {
        return this.post("query", page);
    }

    getGroupActive(commodityId) {
        // const params = new HttpParams().set('id', commodityId);
        // return this.get("getVoById", params);
    }

    getMyCollage(memberId) {
        // const params = new HttpParams().set('memberId', memberId);
        // return this.get("myCollage", params);
    }

    getGroupProduct(commodityId) {
        const params = new HttpParams().set('id', commodityId);
        return this.get("getVoById", params);
    }

    getOpenGroups(groupBuyProductId) {
        this.setNameSpace = "groupBuyOrder";
        const params = new HttpParams().set('groupBuyProductId', groupBuyProductId);
        return this.get("getOpenGroups", params)
    }

    queryMyGroupOrder(page) {
        this.setNameSpace = "groupBuyOrder";
        return this.post("query", page);
    }

    getLatestGroupBuyActivity(){
        return this.get("getLatestGroupBuyActivity");
    }


}
