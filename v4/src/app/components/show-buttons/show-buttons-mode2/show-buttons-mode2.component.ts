import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../../../services/member-service/member";
import {NativeProvider} from "../../../../services/native-service/native";

@Component({
    selector: 'app-show-buttons-mode2',
    templateUrl: './show-buttons-mode2.component.html',
    styleUrls: ['./show-buttons-mode2.component.scss']
})
export class ShowButtonsMode2Component implements OnInit {

    constructor(public nativeProvider: NativeProvider, private navCtrl: NavController) {
    }

    ngOnInit() {
    }

    goCouponReceive() {
        this.navCtrl.navigateForward("CouponReceivePage")
    }

    goFlashPurchase() {
        this.navCtrl.navigateForward("FlashPurchasePage");
    }

    goSignIn() {
        this.navCtrl.navigateForward("SignInPage");
    }

    goServiceCenter() {
        this.navCtrl.navigateForward("ServiceCenterPage")
    }


    goNationalGroupPurchase() {
        this.navCtrl.navigateForward("NationalGroupPurchasePage")
    }

    goRecommend() {
        this.navCtrl.navigateForward("RecommendPage")
    }

    goCommunityGrouPurchase() {
        this.navCtrl.navigateForward("CommunityGroupPurchasePage");
    }

    goCashbackGroupPurchase() {
        this.navCtrl.navigateForward("CashbackGroupPurchasePage");
    }


    goInvitesCourtesy() {
        if (!MemberProvider.isLogin()) {
            this.nativeProvider.showToastFormI4("请先登录");
            return;
        }
        this.navCtrl.navigateForward("InvitesCourtesyPage")
    }

    goSalesArea() {
        this.navCtrl.navigateForward(["SalesAreaPage", {id:1}]);
    }

    goAreaGift() {
        this.navCtrl.navigateForward("AreaGiftPage");
    }

}
