import { Component, OnInit } from '@angular/core';
import {ModalController} from "@ionic/angular";

@Component({
  selector: 'app-share-click-modal',
  templateUrl: './share-click-modal.page.html',
  styleUrls: ['./share-click-modal.page.scss'],
})
export class ShareClickModalPage implements OnInit {

  constructor(public modalCtrl: ModalController) { }

  ngOnInit() {
  }

    goBack() {
        this.modalCtrl.dismiss();
    }
}
