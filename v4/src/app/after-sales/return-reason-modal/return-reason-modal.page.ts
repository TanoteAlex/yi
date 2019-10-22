import {Component, OnInit} from '@angular/core';
import {ModalController, NavController, NavParams} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {AfterSaleService} from "../../../services/order-service/after-sale.service";
import {NativeProvider} from "../../../services/native-service/native";
import {SUCCESS} from "../../Constants";

@Component({
    selector: 'app-return-reason-modal',
    templateUrl: './return-reason-modal.page.html',
    styleUrls: ['./return-reason-modal.page.scss'],
})
export class ReturnReasonModalPage implements OnInit {
    reasonVo;
    afterSaleType;

    constructor(public navParam: NavParams, public navCtrl: NavController, public route: ActivatedRoute, public modalCtrl: ModalController,
                public afterSaleProvider: AfterSaleService, public nativeProvider: NativeProvider) {
        this.afterSaleType = this.navParam.data.afterSaleType;
    }
    ngOnInit() {
        if(this.afterSaleType==0){
            this.reasonVo=['未收到货', '已收到货'];
            return
        }
        this.afterSaleProvider.getReturnReasons().then(e => {
            if (this.afterSaleType=='未收到货'){
                this.reasonVo=e.filter( e => {return e.afterSaleType==1}).map( e => {return e.reason})
            }
            if(this.afterSaleType=='已收到货'){
                this.reasonVo=e.filter( e => {return e.afterSaleType==2}).map( e => {return e.reason})
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        })
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

    selectReason(item) {
        this.modalCtrl.dismiss(item);
    }
}
