import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";

@Component({
    selector: 'app-service-modal',
    templateUrl: './service-modal.page.html',
    styleUrls: ['./service-modal.page.scss'],
})
export class ServiceModalPage implements OnInit {

    data;
    sendNode=["","下单支付","确认收货","商品评论","三包售后到期"];
    list=[];

    constructor(public navParams: NavParams, public modalCtrl: ModalController) {
        this.data=this.navParams.data.couponGrantConfig
    }

    ngOnInit() {
        if (this.data.grantStrategy == 1) {
            let content = `在${this.sendNode[this.data.grantNode]}后，一次性返还${this.data.coupon.parValue}元代金券`;
            this.list.push(content);
        }else if(this.data.grantStrategy == 2) {
            this.data.couponGrantSteps.forEach( e => {
                let content = `在${this.sendNode[e.grantNode]}后，返还${(e.grantRate/100*this.data.coupon.parValue).toFixed(2)}元代金券`;
                this.list.push(content);
            });
        }
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

}
