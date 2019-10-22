import { Component, OnInit } from '@angular/core';
import {NativeProvider} from '../../../services/native-service/native';
import {ModalController, NavController} from '@ionic/angular';
import {ShareData} from '../../../domain/original/share-data.model';
import {MemberProvider} from '../../../services/member-service/member';
import {AppConfig} from '../../app.config';
import {inputHandle} from '../../../util/bug-util';
import {SmsService} from '../../../services/common-service/sms.service';
import {ActivatedRoute} from '@angular/router';
import {StringUtils} from '../../../util/StringUtils';
import {InviteAttentionPage} from '../invite-attention/invite-attention.page';
import {SUCCESS} from '../../Constants';
import {WechatService} from '../../../services/wechat-service/wechat.service';
import {ObjectUtils} from '../../../util/object-utils';

@Component({
  selector: 'app-invite-register',
  templateUrl: './invite-register.page.html',
  styleUrls: ['./invite-register.page.scss'],
})
export class InviteRegisterPage implements OnInit {
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

    constructor(public smsProvider: SmsService, public nativeProvider: NativeProvider, public memberProvider: MemberProvider,
                public wechatService: WechatService,public modalCtrl: ModalController,public navCtrl: NavController,
                public route: ActivatedRoute, public appConfig: AppConfig) {
    }

    ngOnInit() {
        //通过分享商品，需要注册，获取传来的信息
        if (this.route.params["value"].shareData) {
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
                this.shareData.preMemberId = href.split('preMemberId=')[1].split('?')[0];

            }
        }
        // console.log("InviteRegisterPage=>this.shareData="+JSON.stringify(this.shareData))

        inputHandle();
    }

    goToRegister() {

      if(!this.isConfirm()){
        return;
      }

        let member = {
            password: this.params.usertel.toString().substr(5),
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

        this.memberProvider.register(member).then(e => {
            if (e.result == SUCCESS) {
                this.nativeProvider.showToastFormI4("注册成功", () => {
                    this.memberProvider.setLoginMember(e.data);
                    this.inviteAttention();
                });
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    //确认按钮可按条件
    isConfirm(): boolean {
        if (StringUtils.isNotBlank(this.params.usertel)) {
            return this.isPoneAvailable(this.params.usertel);
        }else{
            this.nativeProvider.showToastFormI4("请输入手机号");
            return false;
        }
        if (StringUtils.isNotBlank(this.params.vcode)) {
            return true;
        }else{
            this.nativeProvider.showToastFormI4("请输入验证码");
            return false;
        }

    }

    isPoneAvailable($poneInput) {
        var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
        if (!myreg.test($poneInput)) {
            this.nativeProvider.showToastFormI4("手机号格式错误，请核对");
            return false;
        } else {
            return true;
        }
    }

    //验证码参数
    verifyCode: any = {
        verifyCodeTips: "获取验证码",
        countdown: 59,
        disable: true
    };

    setTime() {
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
            this.setTime();
        }, 1000);
    }

    getCode() {
        //验证手机号是否有填写正确
        if (StringUtils.isNotBlank(this.params.usertel)) {
            if(!this.isPoneAvailable(this.params.usertel)){
                return ;
            }
        }else{
            this.nativeProvider.showToastFormI4("请输入手机号码");
            return ;
        }

        this.smsProvider.sendRegisterCode(this.params.usertel.toString()).then(e => {
            if (e.result == SUCCESS) {
                //发送验证码成功后开始倒计时
                this.verifyCode.disable = false;
                this.setTime();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    async inviteAttention(){
        const modal = await this.modalCtrl.create({
                component: InviteAttentionPage,
            }
        );
        await modal.present();
    }

    goToWechatLogin(){
        // this.navCtrl.navigateRoot(['LoginPage',{isPreviousPage:false}]);
        this.wechatService.getWechatId("toInvitesHome");
    }
}
