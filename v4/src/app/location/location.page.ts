import {Component, OnInit} from '@angular/core';
import {MemberProvider} from "../../services/member-service/member";
import {Events, NavController} from "@ionic/angular";
import {REFRESH_CUSTOMERCENTER} from "../Constants";
import {LocationService} from "../../services/common-service/location.service";

@Component({
    selector: 'app-location',
    templateUrl: './location.page.html',
    styleUrls: ['./location.page.scss'],
})
export class LocationPage implements OnInit {
    ngOnInit() {
    }

    searchQuery: string = '';
    searchItems: string[];
    isShowSearchResult = false;
    localCity: string = " 请定位";
    currentCity: string;

    vm: {
        selectedValue: any;
        groups: any[];
    } = {
        selectedValue: '',
        groups: []
    };

    hotCity = ["广东省", "上海市", "北京市"];
    cityListIndex: number = 0;

    provinces = [];

    items;

    constructor(public locationProvider: LocationService, public events: Events, public memberService: MemberProvider, public navCtrl: NavController) {
        // this.vm.groups = CITY;
        this.getLocation();

        // this.city = CITY;
    }

    ionViewWillEnter() {
        this.currentCity = this.memberService.getLocationInfo();
        this.cityListIndex = this.hotCity.indexOf(this.currentCity);

        this.locationProvider.getProvince().then(e => {
            e.data.forEach(e1 => {
                if (e1.area.name) {
                    this.provinces.push(e1.area.name);
                }
                this.initializeItems();
                this.getHotCity();
            })
        })
    }


    getLocation() {
        this.memberService.getLocation();
        this.localCity = this.memberService.getGpsLocation();

    }

    initializeItems() {
        this.items = this.provinces;
    }

    getItems(ev: any) {
        this.initializeItems();
        const val = ev.target.value;

        if (val && val.trim() != '') {
            this.items = this.items.filter((item) => {
                return (item.toLowerCase().indexOf(val.toLowerCase()) > -1);
            })
        }
    }

    selectCity(item, i?) {
        if (i != undefined) {
            this.cityListIndex = i;
        }
        this.memberService.setLocation(item, true);
        this.events.publish(REFRESH_CUSTOMERCENTER);
        this.navCtrl.goBack();
    }

    getCurrentCity() {
        this.navCtrl.goBack();
    }

    getHotCity() {
        if (this.provinces.length > 3) {
            this.hotCity = this.provinces.slice(0, 3);
        }
    }
}
