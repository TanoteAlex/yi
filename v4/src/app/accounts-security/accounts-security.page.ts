import {Component} from '@angular/core';
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-accounts-security',
    templateUrl: './accounts-security.page.html',
    styleUrls: ['./accounts-security.page.scss'],
})
export class AccountsSecurityPage {
    phone;
    hasPaypsw: boolean = false;

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController) {

    }

    ionViewWillEnter() {
        this.phone = MemberProvider.getLoginMember().phone;
        this.hasPaypsw = MemberProvider.getLoginMember().payPassword == null;
    }

    goPaymentPwd() {
        this.navCtrl.navigateForward("ChangePaymentPwdPage")
    }

    goChangePhoneId() {
        this.navCtrl.navigateForward("ChangePhonePage")
    }

}
