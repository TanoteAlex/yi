import {Injectable} from '@angular/core';
import {HttpServiceProvider} from "../http-service/http-service";
import {AlertController} from "@ionic/angular";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AppConfig} from "../../app/app.config";

@Injectable()
export class GiftService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "giftBag");
    }

    queryMySend(pageQuery) {
        return this.post('queryMySend', pageQuery);
    }

    queryMyReceive(pageQuery) {
        return this.post('queryMyReceive', pageQuery);
    }

    createGiftBag(giftBagBo) {
        return this.post('createGiftBag', giftBagBo);
    }

    getGiftBagDetail(giftBagId) {
        const params = new HttpParams().set('giftBagId', giftBagId);
        return this.get('getGiftBagDetail', params);
    }

    receiveGift(giftBagBo) {
        return this.post('receiveGift', giftBagBo);
    }
}

//  生成用
// /**
//  * 会员（member表ID）礼包所属人或领取人账号
//  */
// private MemberBo member;
//
// /**
//  * 货品（product表ID）
//  */
// private ProductBo product;
//
// /**
//  * 数量
//  */
// private int quantity;
//
// /**
//  * 自定义祝福语
//  */
// private String blessWord;
//  领取用
// /**
//  * 收货人
//  */
// private String consignee;
// /**
//  * 收货人电话
//  */
// private String consigneePhone;
// /**
//  * 收货人地址
//  */
// private String consigneeAddr;

