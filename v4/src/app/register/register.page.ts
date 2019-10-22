import {Component, OnInit} from '@angular/core';
import {SmsService} from "../../services/common-service/sms.service";
import {NativeProvider} from "../../services/native-service/native";
import {REFRESH_CUSTOMERCENTER} from "../Constants";
import {MemberProvider} from "../../services/member-service/member";
import {NavController} from "@ionic/angular";
import {ShareData} from "../../domain/original/share-data.model";
import {ActivatedRoute} from "@angular/router";
import {inputHandle} from "../../util/bug-util";
import {AppConfig} from "../app.config";
import {isUndefined} from "util";
import {ObjectUtils} from "../../util/object-utils";

@Component({
    selector: 'app-register',
    templateUrl: './register.page.html',
    styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData: ShareData;

    //页面参数
    params = {
        usertel: '',
        newpass: '',
        vcode: '',
    };


    isAgree: boolean = true;

    constructor(public smsProvider: SmsService, public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController,
                public route: ActivatedRoute, public appConfig: AppConfig) {
    }

    ngOnInit() {
        //通过分享商品，需要注册，获取传来的信息
        if (!ObjectUtils.isUndefined(this.route.params["value"].shareData) ) {
            this.shareData = JSON.parse(this.route.params["value"].shareData);
        }

        //通过邀请，在链接获取数据
        let href = window.location.href;
        if (href.indexOf('&unionId') != -1) {
            let split = href.split('?')[1].split('&');
            this.shareData = {
                openId: split[0].replace('openId=', ''),
                unionId: split[1].replace('unionId=', ''),
                preMemberId: ''
            };
            if (href.indexOf('preMemberId') != -1) {
                this.shareData.preMemberId = href.split('preMemberId=')[1].split('?')[0]
            }
        }

        inputHandle();
    }

    goHome() {
        if (!this.isAgree) {
            this.nativeProvider.showToastFormI4("请仔细阅读《用户服务协议》并勾选");
            return
        }

        let member = {
            password: this.params.newpass,
            phone: this.params.usertel,
            smsCode: this.params.vcode,
            spOpenId: '',
            unionId: '',
            parentId: '',
            appOpenId: '',
        };

        if (this.shareData) {
            member.spOpenId = this.shareData.openId;
            member.unionId = this.shareData.unionId;
            member.parentId = this.shareData.preMemberId;
        }

        if (this.appConfig.isApp && this.route.params["value"].appOpenId) {
            member.appOpenId = this.route.params["value"].appOpenId;
            member.unionId = this.route.params["value"].unionId;
        }

        member.parentId = 84+'';
        this.memberProvider.register(member).then(e => {
            if (e.result == 'SUCCESS') {
                this.nativeProvider.showToastFormI4("注册成功", () => {
                    this.memberProvider.setLoginMember(e.data);
                    // if (window.location.href.indexOf('&unionId') == -1) {
                    //     this.navCtrl.goBack();
                    // } else {
                    //     this.navCtrl.navigateRoot('/app/tabs/home');
                    // }
                    this.navCtrl.navigateRoot('/app/tabs/home');
                });
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    //确认按钮可按条件
    isConfirm(): boolean {
        if (this.params.newpass !== "" && this.params.usertel.toString().length == 11) {
            if (this.isAgree && this.params.vcode != '') {
                return false;
            }
        }
    }

    //密码显隐
    pswtype: string = "password";
    show: any = "assets/app_icon/customer/眼睛-close@2x.png";

    changShow() {
        if (this.show == "assets/app_icon/customer/眼睛-open@2x.png") {
            this.show = "assets/app_icon/customer/眼睛-close@2x.png";
            this.pswtype = "password";
        } else {
            this.show = "assets/app_icon/customer/眼睛-open@2x.png";
            this.pswtype = "text";
        }
    }

    //验证码参数
    verifyCode: any = {
        verifyCodeTips: "发送验证码",
        countdown: 59,
        disable: true
    };

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

    getCode() {
        //验证手机号是否有填写正确
        if (this.params.usertel.toString().length !== 11) {
            this.nativeProvider.showToastFormI4("请输入正确的手机号码");
            return;
        }

        this.smsProvider.sendRegisterCode(this.params.usertel.toString()).then(e => {
            if (e.result == "SUCCESS") {
                //发送验证码成功后开始倒计时
                this.verifyCode.disable = false;
                this.settime();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    goAgreement() {
        this.navCtrl.navigateForward("AgreementPage")
    }
}
