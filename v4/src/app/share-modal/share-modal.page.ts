import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";
import {WechatforAppService} from "../../services/forApp-service/wechat.service";
import {SUCCESS} from "../Constants";
import {NativeProvider} from "../../services/native-service/native";
import {WechatService} from "../../services/wechat-service/wechat.service";

@Component({
    selector: 'app-share-modal',
    templateUrl: './share-modal.page.html',
    styleUrls: ['./share-modal.page.scss'],
})
export class ShareModalPage implements OnInit {
    url;
    commodityId;
    preMemberId;
    imgBase:string = 'assets/loading.gif';
    constructor(public modalCtrl: ModalController, public navParams: NavParams, public wechatProvider: WechatService, public nativeProvider: NativeProvider) {
        this.url = this.navParams.data.url;
        this.commodityId = this.navParams.data.commodityId;
        this.preMemberId = this.navParams.data.preMemberId;

    }

    ngOnInit() {
        this.wechatProvider.getShareImg(this.url, this.commodityId).then( e => {
            if (e.result==SUCCESS){
                this.imgBase = e.data;
            } else{
                this.nativeProvider.showToastFormI4(e.message);
            }
        })
    }
}
