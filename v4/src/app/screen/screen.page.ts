import {Component} from '@angular/core';
import {ModalController, NavController} from "@ionic/angular";

@Component({
    selector: 'app-screen',
    templateUrl: './screen.page.html',
    styleUrls: ['./screen.page.scss'],
})
export class ScreenPage {

    list1 = [{min: 0, max: 49}, {min: 50, max: 99}, {min: 100, max: 199}, {min: 200, max: 599}, {
        min: 600,
        max: 999
    }, {min: 1000, max: 1e99}];
    list2 = ["中国", "法国", "中国", "意大利", "中国", "西班牙", "瑞士", "尼日利亚"]

    min;
    max;

    constructor(public navCtrl: NavController, public modalCtrl: ModalController) {
    }

    setPrice(item) {
        this.min = item.min;
        this.max = item.max;
    }

    reset() {
        this.min = null;
        this.max = null;
    }

    confirm() {
        //todo 回传 min&max
        this.modalCtrl.dismiss([this.max, this.min])
    }

}
