import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../../services/member-service/member";
import {SUCCESS} from "../../Constants";
import {NativeProvider} from "../../../services/native-service/native";


@Component({
    selector: 'app-invited-member',
    templateUrl: './invited-member.page.html',
    styleUrls: ['./invited-member.page.scss'],
})
export class InvitedMemberPage implements OnInit {
    invitesNum = {
        totalNum: 0,
        successNum: 0,
        failureNum: 0,
        inviteNum: 0
    };

    failureMembers;
    successOrders;

    memberId;
    constructor(public navCtrl: NavController, public memberProvider: MemberProvider, public nativeProvider: NativeProvider) {

    }

    ngOnInit() {
        this.memberId =MemberProvider.getLoginMember().id;
        this.nativeProvider.showLoadingForI4().then(() => {
            this.getInvitesNum();
            this.getInviteOrderAndMember()
        });

    }

    goInviteCode() {
        this.navCtrl.goBack(false);
    }

    getInvitesNum() {
        this.memberProvider.getInviteNum(this.memberId).then(e => {
            this.nativeProvider.hideLoadingForI4();
            if (e.result == SUCCESS) {
                this.invitesNum.successNum = e.data.successNum;
                this.invitesNum.totalNum = e.data.totalNum;
                this.invitesNum.failureNum = e.data.failureNum;
            } else {
                this.nativeProvider.showToastFormI4(e.message)
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.nativeProvider.hideLoadingForI4();
        })
    }

    getInviteOrderAndMember() {
        this.memberProvider.getInviteOrderAndMember(this.memberId).then( e => {
            this.nativeProvider.hideLoadingForI4();
            if (e.result == SUCCESS) {
                this.failureMembers = e.data.failureMembers;
                this.successOrders = e.data.successOrders;
            } else {
                this.nativeProvider.showToastFormI4(e.message)
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.nativeProvider.hideLoadingForI4();
        })
    }

    remind(phone){

    }

}
