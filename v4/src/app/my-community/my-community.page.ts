import {Component} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {AlertController, Events, ModalController, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from "../../services/native-service/native";
import {REFRESH_CUSTOMERCENTER, REFRESH_HOME} from "../Constants";
import {ChooseCommunityModalPage} from "../choose-community-modal/choose-community-modal.page";

@Component({
    selector: 'app-my-community',
    templateUrl: './my-community.page.html',
    styleUrls: ['./my-community.page.scss'],
})
export class MyCommunityPage {
    item;

    communityList = [];
    member;

    constructor(public modalCtrl: ModalController, public events: Events, public alertCtrl: AlertController, public sanitizer: DomSanitizer, public domSanitizer: DomSanitizer, public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController) {
        this.member = MemberProvider.getLoginMember();
    }

    ionViewWillEnter() {
        this.memberProvider.getCommunityInfo(this.member.id).then(e => {
            if (e.result == "SUCCESS") {
                if (e.data) {
                    this.item = e.data;

                    this.item.description = this.item.description.replace('<p', '<p style="font-size:0!important" ');
                    this.item.description = this.domSanitizer.bypassSecurityTrustHtml(this.item.description);
                } else {
                    this.item = {
                        address: "暂无小区",
                        description: ""
                    }
                }
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    ionViewWillLeave() {
        /*this.events.publish(REFRESH_HOME);*/
        this.events.publish(REFRESH_CUSTOMERCENTER);
    }

    async chooseCommunity() {
        const modal = await this.modalCtrl.create({
                component: ChooseCommunityModalPage,
            }
        );
        await modal.present();

        await modal.onDidDismiss().then(data => {
            if (data.data) {
                this.item = data.data;

                this.item.description=this.item.description.replace('<p','<p style="font-size:0!important" ');
                this.item.description = this.domSanitizer.bypassSecurityTrustHtml(this.item.description);
            }
        });
    }

    goCommunityPerformance() {
        this.navCtrl.navigateForward(["CommunityPerformancePage", {communityId: this.item.id}]);
    }

    goCommunityTeam() {
        this.navCtrl.navigateForward(["MyTeamPage", {communityId: this.item.id}]);
    }

}
