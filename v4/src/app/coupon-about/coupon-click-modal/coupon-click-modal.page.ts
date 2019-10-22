import {Component, OnInit} from '@angular/core';
import {ModalController, NavController, NavParams} from "@ionic/angular";

@Component({
    selector: 'app-coupon-click-modal',
    templateUrl: './coupon-click-modal.page.html',
    styleUrls: ['./coupon-click-modal.page.scss'],
})
export class CouponClickModalPage implements OnInit {

    text: string = "全部产品适用，可赠送他人适用，代金券可叠加使用，也可与优惠券叠加使用，使用期限长";

    price;
    startTime;
    endTime;

    constructor(public modalCtrl: ModalController, public navCtrl: NavController,public navParams: NavParams,) {
        this.price = this.navParams.data.price;
        this.startTime = this.navParams.data.startTime;
        this.endTime = this.navParams.data.endTime;
    }

    ngOnInit() {
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

    goHome() {
        this.modalCtrl.dismiss().then(() => {
            this.navCtrl.navigateForward('/app/tabs/home')
        });
    }

    getTime(time) {
        return time.substr(0, 10).replace(/-/g,'.');
    }

}
