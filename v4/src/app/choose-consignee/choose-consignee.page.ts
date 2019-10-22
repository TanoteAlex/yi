import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Events, ModalController, NavController, NavParams} from '@ionic/angular';
import {MemberProvider} from '../../services/member-service/member';
import {NativeProvider} from '../../services/native-service/native';
import {ShippingAddress} from '../../domain/original/shipping-address.model';
import {REFRESH_CHOOSECONSIGNEE} from '../Constants';
import {ConsigneeModalPage} from '../consignee-modal/consignee-modal.page';

@Component({
    selector: 'app-choose-consignee',
    templateUrl: './choose-consignee.page.html',
    styleUrls: ['./choose-consignee.page.scss'],
})
export class ChooseConsigneePage {

    shippingVo: ShippingAddress[] = [];

    check: boolean;

    selectConsignee;

    constructor(public events: Events, public navParam: NavParams, public modalCtrl: ModalController, public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public route: ActivatedRoute) {
        this.events.subscribe(REFRESH_CHOOSECONSIGNEE, () => {
            this.getData();
        });
    }

    ionViewWillEnter() {
        this.getData();
    }

    getData() {
        this.memberProvider.getShippingAddress(MemberProvider.getLoginMember().id).then(e => {
            this.shippingVo = e.data;
            let shippingAddresses = this.shippingVo.filter(e1 => e1.id == this.navParam.data.shippingAddress.id);
            if (shippingAddresses.length > 0) {
                this.selectConsignee = shippingAddresses[0];
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    /*当前选择√判断*/
    getCheck(item) {
        if (this.navParam.data.shippingAddress) {
            return item.id == this.navParam.data.shippingAddress.id;
        } else {
            return item.defaulted == 1;
        }
    }

    goWriteOrder(item) {
        this.modalCtrl.dismiss(item);
    }

    async goEditConsignee(item) {
        const modal = await this.modalCtrl.create({
            component: ConsigneeModalPage,
            componentProps: {address: item}
        });
        await modal.present();
    }

    async goAddConsignee() {
        const modal = await this.modalCtrl.create({
            component: ConsigneeModalPage,
        });
        await modal.present();
    }

}
