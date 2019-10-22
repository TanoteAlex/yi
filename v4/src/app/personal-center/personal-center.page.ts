import {Component, OnInit} from '@angular/core';
import {Events, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {Member} from "../../domain/original/member.model";
import {REFRESH_HOME} from "../Constants";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-personal-center',
    templateUrl: './personal-center.page.html',
    styleUrls: ['./personal-center.page.scss'],
})
export class PersonalCenterPage implements OnInit {

    member: Member;

    ngOnInit() {
    }

    constructor(public navCtrl: NavController, public memberProvider: MemberProvider, public events: Events, public nativeProvider: NativeProvider, ) {
        this.member = MemberProvider.getLoginMember();
    }

    ionViewWillEnter() {

    }

    goPersonalInfo() {
        this.navCtrl.navigateForward("PersonalInfoPage")
    }

    goModifyPwd() {
        this.navCtrl.navigateForward("ChangePwdPage")
    }

    goMemberGrade() {
        this.navCtrl.navigateForward("MemberGradePage")
    }

    goAccountsSecurity() {
        this.navCtrl.navigateForward("AccountsSecurityPage")
    }

    loginOut() {
        this.memberProvider.loginOut();
        this.nativeProvider.showToastFormI4("已退出登录", () => {
            this.events.publish(REFRESH_HOME);
            this.navCtrl.navigateRoot("/app/tabs/home");
        });
    }

    goInviteCode() {
        this.navCtrl.navigateForward("InvitesCodePage");
    }

}
