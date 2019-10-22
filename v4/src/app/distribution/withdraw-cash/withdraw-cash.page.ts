import {Component, OnInit} from '@angular/core';
import {SUCCESS} from "../../Constants";
import {MemberProvider} from "../../../services/member-service/member";
import {AlertController} from "@ionic/angular";
import {Member} from "../../../domain/original/member.model";
import {NativeProvider} from "../../../services/native-service/native";
import {WechatService} from "../../../services/wechat-service/wechat.service";

@Component({
    selector: 'app-withdraw-cash',
    templateUrl: './withdraw-cash.page.html',
    styleUrls: ['./withdraw-cash.page.scss'],
})
export class WithdrawCashPage implements OnInit {

    mode = 'wechat';

    member: Member;

    money;

    constructor(public memberProvider: MemberProvider, public alertCtrl: AlertController, public weChatProvider: WechatService, public nativeProvider: NativeProvider
    ) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.memberProvider.getMember(MemberProvider.getLoginMember().id).then(e => {
            this.memberProvider.setLoginMember(e.data);
            this.member = e.data;
        })
    }

    withdrawMode(mode) {
        this.mode = mode;
    }

    async commit() {
        if (this.member.account.cashableCommission < this.money) {
            this.nativeProvider.showToastFormI4("提现金额大于可提现金额");
            return;
        }

        if (this.member.account.cashableCommission <= 1) {
            this.nativeProvider.showToastFormI4("提现金额不得小于1元");
            return;
        }

        const alert = await this.alertCtrl.create({
            header: '确认提现？',
            buttons: [
                {
                    text: '取消',
                    role: 'cancel',
                    cssClass: 'secondary',
                    handler: (blah) => {

                    }
                }, {
                    text: '确认',
                    handler: () => {
                        this.memberProvider.withdrawCommission(MemberProvider.getLoginMember().id, this.money).then(e => {
                            this.alert();
                        })
                    }
                }
            ]
        });
        await alert.present();
    }

    async alert() {
        const alert = await this.alertCtrl.create({
            header: '提现申请已提交',
            buttons: ['OK'],
        });
        this.ionViewWillEnter();
        await alert.present()
    }

}
