import {Component, Input} from '@angular/core';
import {AlertController, Events, NavController} from '@ionic/angular';
import {MemberProvider} from '../../../services/member-service/member';
import {NativeProvider} from '../../../services/native-service/native';
import {REFRESH_CUSTOMERCENTER} from '../../Constants';
import {SmsService} from '../../../services/common-service/sms.service';
import {LoginPage} from '../../login/login.page';
import {WechatService} from '../../../services/wechat-service/wechat.service';
import {AppConfig} from '../../app.config';

@Component({
    selector: 'base-login',
    templateUrl: './base.component.html',
    styleUrls: ['./base.component.scss']
})
export class BaseComponent {

    @Input() type: string;

    //页面参数
    params = {
        usertel: '',
        newpass: '',
        vcode: '',
    };

    @Input()
    isAgree: boolean = true;

    //密码placeholder
    @Input()  pwplace: string = '登录密码';

    @Input() wechatReg = {
                openId: '',
                unionId: '',
                isWechatReg: false
            };

    @Input() preMemberId;

    @Input() previousPage: boolean = true;

    constructor(public smsProvider: SmsService, public nativeProvider: NativeProvider, public events: Events, public memberProvider: MemberProvider,
                public navCtrl: NavController, public alertCtrl: AlertController, public wechatProvider: WechatService, public appConfig: AppConfig) {
    }

    //确认按钮可按条件
    isConfirm(): boolean {
        if (this.params.newpass !== '' && this.params.usertel.toString().length == 11) {
            switch (this.type) {
                case 'login':
                    return false;
                case 'register':
                    if (this.isAgree && this.params.vcode != '') {
                        return false;
                    }
                    break;
                case 'forget-password':
                    if (this.params.vcode != '') {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    //密码显隐
    pswtype: string = 'password';
    show: any = 'assets/app_icon/customer/眼睛-close@2x.png';

    changShow() {
        if (this.show == 'assets/app_icon/customer/眼睛-open@2x.png') {
            this.show = 'assets/app_icon/customer/眼睛-close@2x.png';
            this.pswtype = 'password';
        } else {
            this.show = 'assets/app_icon/customer/眼睛-open@2x.png';
            this.pswtype = 'text';
        }
    }

    //验证码参数
    verifyCode: any = {
        verifyCodeTips: '发送验证码',
        countdown: 59,
        disable: true
    };

    settime() {
        if (this.verifyCode.countdown == 1) {
            this.verifyCode.countdown = 59;
            this.verifyCode.verifyCodeTips = '获取验证码';
            this.verifyCode.disable = true;
            return;
        } else {
            this.verifyCode.countdown--;
        }

        this.verifyCode.verifyCodeTips = this.verifyCode.countdown + 's';
        setTimeout(() => {
            this.verifyCode.verifyCodeTips = this.verifyCode.countdown + 's';
            this.settime();
        }, 1000);
    }

    getCode() {
        //验证手机号是否有填写正确
        if (this.params.usertel.toString().length !== 11) {
            this.nativeProvider.showToastFormI4('请输入正确的手机号码');
            return;
        }

        switch (this.type) {
            case 'register':
                this.smsProvider.sendRegisterCode(this.params.usertel.toString()).then(e => {
                    if (e.result == 'SUCCESS') {
                        //发送验证码成功后开始倒计时
                        this.verifyCode.disable = false;
                        this.settime();
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => this.nativeProvider.showToastFormI4(err.message));
                break;
            case 'forget-password':
                this.smsProvider.sendChangePwdCode(this.params.usertel.toString()).then(e => {
                    if (e.result == 'SUCCESS') {
                        //发送验证码成功后开始倒计时
                        this.verifyCode.disable = false;
                        this.settime();
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => this.nativeProvider.showToastFormI4(err.message));
                break;
        }
    }

    goHome() {
        let member = {
            password: this.params.newpass,
            phone: this.params.usertel,
            smsCode: this.params.vcode,
            openId: null,
            unionId: null,
            parentId: null,
            loginByApp: false,
        };
        if (this.wechatReg || this.wechatReg.isWechatReg) {
            member.openId = this.wechatReg.openId;
            member.unionId = this.wechatReg.unionId;
        }

        if (this.preMemberId) {
            member.parentId = this.preMemberId;
        }

        if (this.appConfig.isApp) {
            member.loginByApp = true;
        }

        switch (this.type) {
            case 'login':
                this.memberProvider.login(member).then(e => {
                    if (e.result == 'SUCCESS') {
                        this.nativeProvider.showToastFormI4('登录成功');
                        this.memberProvider.setLoginMember(e.data);
                        this.events.publish(REFRESH_CUSTOMERCENTER);
                        // if (!e.data.unionId) {
                        //     this.wechatProvider.login(e.data.id);
                        // this.weChatConfirm(MemberProvider.getLoginMember().id);
                        // }else{
                        // this.navCtrl.goBack();
                        // }

                        if(this.previousPage == true){
                            this.navCtrl.goBack();
                        }else{
                            this.navCtrl.navigateRoot('/app/tabs/home')
                        }
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => this.nativeProvider.showToastFormI4(err.message));
                break;
            case 'register':
                //TODO 测试用
                member.parentId=84;
                this.memberProvider.register(member).then(e => {
                    if (e.result == 'SUCCESS') {
                        this.nativeProvider.showToastFormI4('注册成功');
                        if (this.wechatReg.isWechatReg) {
                            window.location.href = `${window.location.href.split('#')[0]}/#/app/tabs/home?openId=${e.data.openId}&unionId=${e.data.unionId}`;
                        } else {
                            this.navCtrl.navigateRoot('LoginPage');
                        }
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err =>
                    this.nativeProvider.showToastFormI4(err.message));
                break;
            case 'forget-password':
                this.memberProvider.forgetPassword(member).then(e => {
                    if (e.result == 'SUCCESS') {
                        this.nativeProvider.showToastFormI4('修改成功，请登录');
                        this.navCtrl.goBack();
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => this.nativeProvider.showToastFormI4(err.message));
                break;
        }
    }


}
