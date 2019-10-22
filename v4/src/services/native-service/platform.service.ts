import {Injectable} from "@angular/core";
import {AppConfig} from "../../app/app.config";
import {WechatService} from "../wechat-service/wechat.service";

@Injectable()
export class platformService {

    constructor(public wechatService: WechatService, public appConfig: AppConfig,) {

    }

    shareDate(shareData) {
        if (this.appConfig.isApp) {

        }else {
            /*let shareData = {
                title: this.commodity.commodityName,
                desc: this.commodity.commodityShortName,
                link: encodeURI(window.location.href.split('#')[0] + 'wechatShare.html?CommodityPage&id=' + this.commodity.id + '&preMemberId=' + memberId + '?'),
                imgUrl: this.commodity.imgPath,
            };*/
            this.wechatService.share(shareData);
        }
    }
}
