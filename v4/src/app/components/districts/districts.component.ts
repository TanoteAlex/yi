import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ModalController} from "@ionic/angular";
import {DistrictModalPage} from "../../district-modal/district-modal.page";
import {DistrictPipe} from "../../../pipes/district/district";

@Component({
    selector: 'districts',
    templateUrl: './districts.component.html',
    styleUrls: ['./districts.component.scss']
})
export class DistrictsComponent implements OnInit {
    /**
     * multiCity格式 110000 110100 110101 或者null null null
     */
    @Input()
    multiCity;

    @Output()
    result: EventEmitter<any> = new EventEmitter();

    provinceCode;
    cityCode;
    districtCode;

    constructor(public modalCtrl: ModalController) {

    }

    ngOnInit() {
        if (this.multiCity == "null null null" || this.multiCity == "undefined undefined undefined") {
            this.multiCity = undefined;
        }
    }

    async openDistrictModal() {
        const modal = await this.modalCtrl.create({
                component: DistrictModalPage,
                componentProps: {multiCity: this.multiCity},
            }
        );
        await modal.present();

        await modal.onDidDismiss().then(data => {
            if (data.data) {
                let multiCity = data.data.split(' ');
                this.provinceCode = multiCity[0];
                this.cityCode = multiCity[1];
                this.districtCode = multiCity[2];
                this.result.emit(data.data);
            }
        });
    }

    showAddress() {
        let districtPipe = new DistrictPipe();
        if (this.multiCity) {
            let multiCity = this.multiCity.split(' ');
            this.provinceCode = multiCity[0];
            this.cityCode = multiCity[1];
            this.districtCode = multiCity[2];
            return districtPipe.transform(this.provinceCode) + ' ' + districtPipe.transform(this.cityCode) + ' ' + districtPipe.transform(this.districtCode)
        } else {
            return false;
        }
    }
}
