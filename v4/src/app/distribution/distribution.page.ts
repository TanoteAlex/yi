import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from "../../services/native-service/native";
import {Member} from "../../domain/original/member.model";
import {SUCCESS} from "../Constants";

@Component({
    selector: 'app-distribution',
    templateUrl: './distribution.page.html',
    styleUrls: ['./distribution.page.scss'],
})
export class DistributionPage implements OnInit {

    member: Member;
    teamAmount = '0';

    constructor(public navCtrl: NavController, public memberProvider: MemberProvider, public nativeProvider: NativeProvider) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.memberProvider.getMember(MemberProvider.getLoginMember().id).then(e => {
            this.memberProvider.setLoginMember(e.data);
            this.member = e.data;
            this.getTeamAmount();
        })
    }

    goMyTeam() {
        this.navCtrl.navigateForward("MyTeamPage")
    }

    getTeamAmount() {
        if (!MemberProvider.isLogin()) {
            return
        }
        this.memberProvider.getMyTeamNum(this.member.id).then(e => {
            if (e.result == SUCCESS) {
                this.teamAmount = e.data;
            }
        })
    }

    goWithDrawCash() {
        this.navCtrl.navigateForward("WithdrawCashPage");
    }

    goMyCode() {
        this.navCtrl.navigateForward("MyCodePage");
    }

    goCommissionInfo() {
        this.navCtrl.navigateForward("CommissionInfoPage");
    }

    goWithdrawDetail() {
        this.navCtrl.navigateForward("WithdrawDetailPage")
    }

    goCommissionOrder() {
        this.navCtrl.navigateForward("CommissionOrderPage")
    }
}
