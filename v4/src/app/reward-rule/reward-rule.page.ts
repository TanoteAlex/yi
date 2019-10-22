import {Component, OnInit} from '@angular/core';
import {Member} from "../../domain/original/member.model";
import {MemberProvider} from "../../services/member-service/member";
import {NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-reward-rule',
    templateUrl: './reward-rule.page.html',
    styleUrls: ['./reward-rule.page.scss'],
})
export class RewardRulePage implements OnInit {

    list: Member[];

    constructor(public memberProvider: MemberProvider, public navCtrl: NavController, public nativeProvider: NativeProvider) {
    }

    ionViewWillEnter() {
        /*this.memberProvider.getRewards(MemberProvider.getLoginMember().id).then(e => {
            if (e.result == "SUCCESS") {
                this.list = e.data;
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });*/
    }

    ngOnInit() {
    }

}
