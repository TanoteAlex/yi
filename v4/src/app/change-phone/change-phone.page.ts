import {Component} from '@angular/core';
import {MemberProvider} from "../../services/member-service/member";
import {AlertController, NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";
import {inputHandle} from "../../util/bug-util";

@Component({
    selector: 'app-change-phone',
    templateUrl: './change-phone.page.html',
    styleUrls: ['./change-phone.page.scss'],
})
export class ChangePhonePage {

    tip = ["手机号码修改成功后需要使用新手机的手机号码进行登录。"];

    params = {
        phone: "",
        vcode: ""
    };

    //验证码参数
    verifyCode: any = {
        verifyCodeTips: "发送验证码",
        countdown: 59,
        disable: true
    };

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public alertCtrl: AlertController) {

    }

    ngOnInit(): void {
        inputHandle();
    }

    goBack() {
        let memberBo = {
            id: MemberProvider.getLoginMember().id,
            phone: this.params.phone,
            smsCode: this.params.vcode
        };
        this.memberProvider.changePhone(memberBo).then(e => {
            if (e.result == "SUCCESS") {
                this.memberProvider.setLoginMember(e.data);
                this.nativeProvider.showToastFormI4("修改成功", ()=>{
                    this.navCtrl.goBack();
                });
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    getCode() {
        //验证手机号是否有填写正确
        if (this.params.phone.toString().length === 11) {
            //发送验证码成功后开始倒计时
            this.verifyCode.disable = false;
            this.settime();
        } else {
            this.nativeProvider.showToastFormI4("请输入正确的手机号码")
        }
    }

    settime() {
        if (this.verifyCode.countdown == 1) {
            this.verifyCode.countdown = 59;
            this.verifyCode.verifyCodeTips = "获取验证码";
            this.verifyCode.disable = true;
            return;
        } else {
            this.verifyCode.countdown--;
        }

        this.verifyCode.verifyCodeTips = this.verifyCode.countdown + "s";
        setTimeout(() => {
            this.verifyCode.verifyCodeTips = this.verifyCode.countdown + "s";
            this.settime();
        }, 1000);
    }

}
