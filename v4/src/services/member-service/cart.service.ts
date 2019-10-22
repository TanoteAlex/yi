import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
  providedIn: 'root'
})
export class CartService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "cart");
    }

    addShopCart(memberId, productId, num) {
        let CartBO = {
            member:{id:memberId},
            product:{id:productId},
            num:num,
        };
        return this.post('addShopCart', CartBO);
    }

    deleteCommodity(memberId, shoppingCartProductId) {
        let CartBO = {
            member:{id:memberId},
            id:shoppingCartProductId,
        };
        return this.post('removeShopCart', CartBO);
    }

    changQuantity(memberId, shoppingCartProductId, quantity) {
        let CartBO = {
            member:{id:memberId},
            id:shoppingCartProductId,
            num:quantity,
        };
        return this.post('changeShopCartNum', CartBO);
    }

    getShoppingCart(memberId) {
        const params = new HttpParams().set('memberId', memberId);
        return this.get("getShopCarts", params);
    }
}
