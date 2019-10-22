import {Component, OnInit} from '@angular/core';
import {MemberProvider} from "../../../../services/member-service/member";
import {NativeProvider} from "../../../../services/native-service/native";
import {Events, NavController} from "@ionic/angular";
import {REFRESH_HOME} from "../../../Constants";

@Component({
    selector: 'app-show-buttons-mode1',
    templateUrl: './show-buttons-mode1.component.html',
    styleUrls: ['./show-buttons-mode1.component.scss']
})
export class ShowButtonsMode1Component implements OnInit {

    member;

    constructor(public events: Events, public nativeProvider: NativeProvider, private navCtrl: NavController) {
        this.member = MemberProvider.getLoginMember();
        this.events.subscribe(REFRESH_HOME, () => {
            this.member = MemberProvider.getLoginMember();
        });
    }

    ngOnInit() {
    }

    goMyCommunity() {
        if (!MemberProvider.isLogin()) {
            this.nativeProvider.showToastFormI4("请先登录");
            return;
        }
        window.location.href = './app/tabs/home';
        setTimeout(() => {
            this.navCtrl.navigateForward("MyCommunityPage");
        }, 100)
    }
}
