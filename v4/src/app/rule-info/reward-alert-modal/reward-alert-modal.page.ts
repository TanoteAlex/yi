import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";

@Component({
    selector: 'app-reward-alert-modal',
    templateUrl: './reward-alert-modal.page.html',
    styleUrls: ['./reward-alert-modal.page.scss'],
})
export class RewardAlertModalPage implements OnInit {
    data = [];

    constructor(public navParams: NavParams, public modalCtrl: ModalController) {
        this.data = this.navParams.data.data[0];
    }

    ngOnInit() {

    }

    goBack() {
        this.modalCtrl.dismiss();
    }
}
