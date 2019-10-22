import {Component} from '@angular/core';
import {AttributeGroup} from "../../../domain/original/attribute-group.model";
import {ModalController, NavParams} from "@ionic/angular";

@Component({
    selector: 'app-params-modal',
    templateUrl: './params-modal.page.html',
    styleUrls: ['./params-modal.page.scss'],
})
export class ParamsModalPage {

    attrGroups: AttributeGroup[] = [];

    constructor(public navParams: NavParams, public modalCtrl: ModalController) {
        this.attrGroups = this.navParams.data.attrGroups;
        for (let i = 0; i < this.attrGroups.length; i++) {
            this.attrGroups[i].attributeArray = "";
            for (let j = 0; j < this.attrGroups[i].attrValues.length; j++) {
                this.attrGroups[i].attributeArray += (this.attrGroups[i].attrValues[j].attrValue) + " ";
            }
        }
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

}
