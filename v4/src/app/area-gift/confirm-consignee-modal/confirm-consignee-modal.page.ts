import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";
import {ConsigneeModalPage} from "../../consignee-modal/consignee-modal.page";
import {MemberProvider} from "../../../services/member-service/member";

@Component({
    selector: 'app-confirm-consignee-modal',
    templateUrl: './confirm-consignee-modal.page.html',
    styleUrls: ['./confirm-consignee-modal.page.scss'],
})
export class ConfirmConsigneeModalPage implements OnInit {

    consigneeData;

    constructor(public navParam: NavParams,public modalCtrl: ModalController, ) {
        this.consigneeData = this.navParam.data.consigneeData
    }

    ngOnInit() {
        console.log(this.consigneeData);
    }

    confirm() {
        this.modalCtrl.dismiss({isConfirm:true,address:this.consigneeData});
    }

    rewrite(){
        this.modalCtrl.dismiss({isConfirm:false,address:this.consigneeData});
    }

}
