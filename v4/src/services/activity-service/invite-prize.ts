import {AppConfig} from '../../app/app.config';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {HttpServiceProvider} from '../http-service/http-service';
import {AlertController} from '@ionic/angular';

@Injectable()
export class InvitePrizeProvider extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "invitePrize");
    }

    receiveIntegral(prizeId,memberId) {
        let params = {id:prizeId,member:{id:memberId}};
        return this.post("receiveIntegral", params);
    }

    receiveCoupon(prizeId,memberId) {
        let params = {id:prizeId,member:{id:memberId}};
        return this.post("receiveCoupon", params);
    }

    receiveCommodity(prizeId,memberId,shippingAddress) {
        let params = {id:prizeId,member:{id:memberId},shippingAddress:shippingAddress};
        return this.post("receiveCommodity", params);
    }

    getInvitePrizeById(prizeId){
        const params = new HttpParams().set('id', prizeId);
        return this.get("getInvitePrizeById", params);
    }


}