import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";

@Component({
    selector: 'app-sign-success-modal',
    templateUrl: './sign-success-modal.page.html',
    styleUrls: ['./sign-success-modal.page.scss'],
})
export class SignSuccessModalPage implements OnInit {
    data;

    constructor(public navParams: NavParams,public modalCtrl: ModalController) {
        this.data = this.navParams.data.data;
    }

    ngOnInit() {
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

}
