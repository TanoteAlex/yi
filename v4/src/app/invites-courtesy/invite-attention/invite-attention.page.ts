import {Component, OnInit} from '@angular/core';
import {ModalController, NavController} from '@ionic/angular';

@Component({
    selector: 'app-invite-attention',
    templateUrl: './invite-attention.page.html',
    styleUrls: ['./invite-attention.page.scss'],
})
export class InviteAttentionPage implements OnInit {

    constructor(public navCtrl: NavController,public modalCtrl: ModalController) {
    }

    ngOnInit() {
    }

    close() {
        this.modalCtrl.dismiss().then(e => {
            this.navCtrl.navigateRoot('/app/tabs/home');
        });
    }
}
