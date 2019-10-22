import {Component} from '@angular/core';
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {NavController} from "@ionic/angular";
import {ShippingAddress} from "../../domain/original/shipping-address.model";

@Component({
    selector: 'app-my-address',
    templateUrl: './my-address.page.html',
    styleUrls: ['./my-address.page.scss'],
})
export class MyAddressPage {

    shippingVo: ShippingAddress[];

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController) {

    }

    ionViewWillEnter() {
        if (MemberProvider.isLogin()) {
            this.memberProvider.getShippingAddress(MemberProvider.getLoginMember().id).then(e => {
                this.shippingVo = e.data;
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
            })
        }
    }

    goAddConsignee() {
        this.navCtrl.navigateForward("ConsigneePage", false)
    }

    changPrefer(item, i) {
        this.shippingVo.forEach(e => {
            e.defaulted = false
        });
        this.shippingVo[i].defaulted = true;
        this.memberProvider.setPreferAddress(MemberProvider.getLoginMember().id, item.id);
    }

    deleteConsignee(item, i) {
        this.shippingVo.splice(i, 1);
        this.memberProvider.deleteAddress(item.id)
    }

    goEditConsignee(item) {
        this.navCtrl.navigateForward(["ConsigneePage", {addressId: item.id, address: item.province + ' ' + item.city + ' ' + item.district}], false)
        // console.log(item);
    }
}
