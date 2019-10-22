import {Component, Input, OnInit} from '@angular/core';
import {ModalController} from "@ionic/angular";

@Component({
    selector: 'center-modal',
    templateUrl: './center-modal.component.html',
    styleUrls: ['./center-modal.component.scss']
})
export class CenterModalComponent implements OnInit {

    /*模态框高度*/
    @Input()
    height;

    /*模态框垂直位置*/
    @Input()
    top;

    @Input()
    showClose:boolean = true;

    constructor(public modalCtrl: ModalController) {
    }

    ngOnInit() {
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

}
