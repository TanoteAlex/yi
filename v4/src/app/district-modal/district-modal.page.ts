import {Component, OnInit} from '@angular/core';
import {ADDRESS} from "../Constants";
import {ModalController, NavParams} from "@ionic/angular";


@Component({
    selector: 'app-district-modal',
    templateUrl: './district-modal.page.html',
    styleUrls: ['./district-modal.page.scss'],
})
export class DistrictModalPage implements OnInit {

    proList = [];
    cityList = [];
    districtList = [];

    selectedPro;
    selectedCity;
    selectedDistrict;

    addressList = ADDRESS;

    constructor(public modalCtrl: ModalController, public navParam: NavParams) {
        if (this.navParam.data.multiCity) {
            let multiCity = this.navParam.data.multiCity.split(' ');
            this.selectedPro = multiCity[0];
            this.selectedCity = multiCity[1];
            this.selectedDistrict = multiCity[2];
        }
    }

    ngOnInit() {
        for (let key in this.addressList) {
            this.proList.push(key);
        }

        if (this.selectedPro) {
            this.selectPro(this.selectedPro, false);
            if (this.selectedCity) {
                this.selectCity(this.selectedCity, false);
            }
        } else {
            this.selectPro(this.proList[0])
        }

    }

    selectPro(item, isInit: boolean = true) {
        this.selectedPro = item;
        this.cityList = [];
        for (let key in this.addressList[this.selectedPro].child) {
            this.cityList.push(key);
        }
        //初始化选择下级的第一个
        if (isInit) {
            this.selectCity(this.cityList[0]);
        }
    }

    selectCity(item, isInit: boolean = true) {
        this.selectedCity = item;
        this.districtList = [];
        //if判断，主要防止选港澳台的报错
        if (!this.addressList[this.selectedPro].child[this.selectedCity]) {
            return
        }

        for (let key in this.addressList[this.selectedPro].child[this.selectedCity].child) {
            this.districtList.push(key);
        }
        //初始化选择下级的第一个
        if (isInit) {
            this.selectedDistrict = this.districtList[0];
        }
    }

    selectDistrict(item) {
        this.selectedDistrict = item;
        this.modalCtrl.dismiss(this.selectedPro + ' ' + this.selectedCity + ' ' + this.selectedDistrict);
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

}
