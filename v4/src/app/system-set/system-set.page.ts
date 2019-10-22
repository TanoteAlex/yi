import {Component, OnInit} from '@angular/core';

import {Events, NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {REFRESH_HOME} from "../Constants";

@Component({
    selector: 'app-system-set',
    templateUrl: './system-set.page.html',
    styleUrls: ['./system-set.page.scss'],
})
export class SystemSetPage implements OnInit {

    ngOnInit() {
    }

    constructor(public events: Events, public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController) {
    }

    goVersionNuber() {
        this.navCtrl.navigateForward("VersionNumberPage")
    }

    goVersionUp() {
        this.navCtrl.navigateForward("VersionUpPage")
    }

    goAccountsSecurity() {
        this.navCtrl.navigateForward("AccountsSecurityPage")
    }

    // 清除缓存
    clearCache() {
        setTimeout(()=>{
            this.nativeProvider.showToastFormI4("清除成功");
        },500)
    }

    loginOut() {
        this.memberProvider.loginOut();
        this.nativeProvider.showToastFormI4("已退出登录", () => {
            this.events.publish(REFRESH_HOME);
            this.navCtrl.navigateRoot("/app/tabs/home");
        });
    }
}
