import {Component} from '@angular/core';
import {AlertController, Events, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {REFRESH_CUSTOMERCENTER, SUCCESS} from "../Constants";
import {NativeProvider} from "../../services/native-service/native";
import {Observable} from "rxjs/internal/Observable";
import {OrderProvider} from "../../services/order-service/order";

@Component({
    selector: 'app-customer-center',
    templateUrl: './customer-center.page.html',
    styleUrls: ['./customer-center.page.scss'],
})
export class CustomerCenterPage {
    member;
    localCity: string;

    customerInfo = {
        waitPayNum: 0,
        waitReceiptNum: 0,
        waitCommentNum: 0,
        couponNum: 0,
        signState: false
    };

    constructor(public orderProvider: OrderProvider, public alertCtrl: AlertController, public nativeProvider: NativeProvider, public navCtrl: NavController, public events: Events,
                public memberProvider: MemberProvider) {
        this.events.subscribe(REFRESH_CUSTOMERCENTER, () => this.ionViewWillEnter())
    }

    ionViewWillEnter() {
        this.localCity = this.memberProvider.getLocationInfo();
        this.member = MemberProvider.getLoginMember();
        this.customerInfo = {
            waitPayNum: 0,
            waitReceiptNum: 0,
            waitCommentNum: 0,
            couponNum: 0,
            signState: false
        };
        if (MemberProvider.isLogin()) {
            this.orderProvider.getOrdersNum(MemberProvider.getLoginMember().id).then(e => {
                if (e.result == SUCCESS) {
                    this.customerInfo = e.data;
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                }
            }, err => this.nativeProvider.showToastFormI4(err.message));

            this.memberProvider.getMember(MemberProvider.getLoginMember().id).then(e => {
                if (e.result == SUCCESS) {
                    this.memberProvider.setLoginMember(e.data);
                    this.member = e.data;
                }
            })
        }
    }

    isLogin() {
        return new Promise((resolve, reject) => {
            if (MemberProvider.isLogin()) {
                resolve();
            } else {
                this.navCtrl.navigateForward("LoginPage");
                return;
            }
        });
    }

    goPersonalCenter() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("PersonalCenterPage")
        });
    }

    goSystemSet() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("SystemSetPage")
        });
    }

    goCoupan() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("CouponPage")
        });
    }

    goInvitesHome() {
        this.isLogin().then(e => {
            // this.navCtrl.navigateForward("InvitesCourtesyPage")
            this.navCtrl.navigateForward(["InvitesHomePage", {preMemberId: this.member.id}]);
        });
    }

    goMyTeam() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("MyTeamPage")
        });
    }

    goLocation() {
        this.navCtrl.navigateForward("LocationPage")
    }

    goDistribution() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("DistributionPage")
        });
    }

    goStorageVolume() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward(["StorageVolumePage", {check: "true"}])
        });
    }

    goFineBalance() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("FineBalancePage")
        });
    }

    goMyBalance() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("MyBalancePage")
        });
    }

    goMyOrderPage(state?) {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward(["MyOrderPage", {state: state}])
        });
    }

    goMemberGrade() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("MemberGradePage")
        });
    }

    goAfterSales() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("AfterSalesPage")
        });
    }

    goMyAddress() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("MyAddressPage");
        });
    }

    goServiceCenter() {
        this.navCtrl.navigateForward("ServiceCenterPage");
    }

    goLogin() {
        if (MemberProvider.isLogin()) {
            this.goPersonalCenter();
        } else {
            this.navCtrl.navigateForward("LoginPage")
        }
    }

    goMyCommunity() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("MyCommunityPage");
        });
    }

    goMyGroup(state) {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward(["GroupOrderPage", {state: state}]);
        });
    }

    goSignIn() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("SignInPage");
        });
    }


    async goTel() {
        let tel = "";  //输入需要拨打的手机号码即可
        if (tel) {
            document.location.href = "tel:" + tel;
        } else {
            const alert = await this.alertCtrl.create({
                message: '暂无联系电话',
                buttons: ['确认']
            });
            await alert.present();
        }
    }


}
