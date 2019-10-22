import { Injectable } from '@angular/core';
import {AppConfig} from "../../app/app.config";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
  providedIn: 'root'
})
export class AfterSaleService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "afterSale");
    }

    /*退货原因*/
    getReturnReasons() {
        return this.get("queryReasons");
    }

    /*申请售后*/
    applyAfterSale(afterSaleOrderBo) {
        return this.post("applyAfterSale", afterSaleOrderBo);
    }

    /*售后详情*/
    getAfterSaleDetail(orderId) {
        const params = new HttpParams().set("saleOrderId", orderId);
        return this.get("getAfterSaleDetail", params);
    }
}
