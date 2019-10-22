import {Component} from '@angular/core';
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {Events, ModalController, NavController} from "@ionic/angular";
import {SignSuccessModalPage} from './sign-success-modal/sign-success-modal.page';
import {REFRESH_CUSTOMERCENTER} from "../Constants";

@Component({
    selector: 'app-sign-in',
    templateUrl: './sign-in.page.html',
    styleUrls: ['./sign-in.page.scss'],
})
export class SignInPage {

    tips = "";

    noSign = "assets/app_icon/home/签到奖励icon@2x.png";
    signed = "assets/app_icon/home/已签到奖励icon@2x.png";

    day = [
        {imgPath: this.noSign, isSign: false, size: 3, prizeName:""},
        {imgPath: this.noSign, isSign: false, size: 3, prizeName:""},
        {imgPath: this.noSign, isSign: false, size: 3, prizeName:""},
        {imgPath: this.noSign, isSign: false, size: 3, prizeName:""},
        {imgPath: this.noSign, isSign: false, size: 6, prizeName:""},
        {imgPath: this.noSign, isSign: false, size: 3, prizeName:""},
        {imgPath: this.noSign, isSign: false, size: 3, prizeName:""},
    ];

    /*已签到天数*/
    signNum: number;

    /*是否已签到*/
    isSigned: boolean = false;

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public modalCtrl: ModalController,
                public events: Events,) {

    }

    ionViewWillEnter() {
        if (MemberProvider.isLogin()) {
            this.memberProvider.getMemberSign(MemberProvider.getLoginMember().id).then(e => {
                this.signNum = e.data.signDays;
                this.isSigned = e.data.signed;
                this.getPrizeName(e.data);
                this.sign();
                this.getTip(e.data.rewards);
            })
        }
    }

    ionViewWillLeave() {
        this.events.publish(REFRESH_CUSTOMERCENTER);
    }

    /*点击签到*/
    toSign() {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward("LoginPage");
        } else {
            this.isSigned = true;
            this.memberProvider.clickSign(MemberProvider.getLoginMember().id).then(e => {
                if (e.result == "SUCCESS") {
                    this.signNum++;
                    this.sign();
                    this.paramsModal(e.data);
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                    this.isSigned = false
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
                this.isSigned = false
            });
        }
    }

    /*具体签到操作过程*/
    sign() {
        for (let i = 0; i < this.signNum; i++) {
            this.day[i].imgPath = this.signed;
            this.day[i].isSign = true;
        }
    }

    async paramsModal(data) {
        const modal = await this.modalCtrl.create({
                component: SignSuccessModalPage,
                componentProps: {data: data}
            }
        );
        await modal.present();
    }

    /**
     * 提示语
     */
    getTip(rewards) {
        rewards.forEach( e => {
            if (e.reminder){
                this.tips += e.reminder;
            }
        })
    }

    formatNum(i) {
        switch (i) {
            case 0:
                return '一';
            case 1:
                return '二';
            case 2:
                return '三';
            case 3:
                return '四';
            case 4:
                return '五';
            case 5:
                return '六';
            case 6:
                return '七';
        }
    }

    getPrizeName(data) {
        data.rewards.forEach(e => {
            this.day[e.signDays - 1].prizeName = e.name;
        })
    }

}
