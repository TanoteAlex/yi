import {Component, OnInit, ViewChild} from '@angular/core';
import {ModalController} from "@ionic/angular";
import {Community} from "../../domain/original/community.model";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from '../../services/native-service/native';
import {LocationService} from "../../services/common-service/location.service";

const unLocation = "请定位";
const errLocation = "地址出错了..";

@Component({
    selector: 'app-choose-community-modal',
    templateUrl: './choose-community-modal.page.html',
    styleUrls: ['./choose-community-modal.page.scss'],
})
export class ChooseCommunityModalPage implements OnInit {
    data;

    proList = [];
    cityList = [];
    communityList = [];

    selectedPro;
    selectedCity;
    selectedCommunity;

    constructor(public modalCtrl: ModalController, public memberProvider: MemberProvider, public nativeProvider: NativeProvider, public locationProvider: LocationService) {
        this.locationProvider.getProvinceCity().then(e => {
            if (e.result == "SUCCESS") {
                this.data = e.data;
                this.getProvince();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    ngOnInit() {
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

    getProvince() {
        for (let key in this.data) {
            this.proList.push(key);
        }
    }

    selectPro(item) {
        this.cityList = this.data[item];
        this.selectedPro = item;
    }

    selectCity(item) {
        this.selectedCity = item;
        this.memberProvider.getCommunityList(item).then(e => {
            if (e.result == "SUCCESS") {
                this.communityList = e.data;
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    selectCommunity(item) {
        this.selectedCommunity = item;
        this.memberProvider.updateCommunity(MemberProvider.getLoginMember().id, item.id).then(e => {
            if (e.result == "SUCCESS") {
                this.memberProvider.setLoginMember(e.data);
                this.modalCtrl.dismiss(item);
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }
}
