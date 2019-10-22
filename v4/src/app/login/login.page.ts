import {Component} from '@angular/core';
import {Events, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {ShareData} from "../../domain/original/share-data.model";
import {ActivatedRoute, ParamMap} from '@angular/router';
import {inputHandle} from "../../util/bug-util";
import {AppConfig} from "../app.config";
import {REFRESH_CUSTOMERCENTER, REFRESH_HOME, SUCCESS} from "../Constants";
import {NativeProvider} from "../../services/native-service/native";
import {WechatforAppService} from "../../services/forApp-service/wechat.service";
import {ObjectUtils} from '../../util/object-utils';

@Component({
    selector: 'app-login',
    templateUrl: './login.page.html',
    styleUrls: ['./login.page.scss'],
})
export class LoginPage {
    href = window.location.href;

    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData: string;
    isApp:boolean;

    previousPage: boolean = true;

    constructor(public wechatService: WechatService, public memberProvider: MemberProvider, public navCtrl: NavController, public route: ActivatedRoute,
                public appConfig: AppConfig, public nativeProvider: NativeProvider, public weChatAppService: WechatforAppService,public events: Events) {
        this.isApp = this.appConfig.isApp;
    }

    ngOnInit() {
        inputHandle();
    }

    ionViewWillEnter() {
        this.route.params.subscribe((params: ParamMap) => {
            let isPreviousPage = params["isPreviousPage"];
            this.previousPage = isPreviousPage;
            if(ObjectUtils.isNotEmpty(params["value"])){
                this.shareData = params["value"].shareData;
            }
        });
    }

    ionViewWillLeave() {
        this.events.publish(REFRESH_CUSTOMERCENTER);
    }

    goForgetPassword() {
        this.navCtrl.navigateForward("ForgetPasswordPage")
    }

    goBack() {
        this.navCtrl.goBack(false)
    }

    weChatLogin() {
        if (this.appConfig.isApp){
            let _this = this;
            this.weChatAppService.auth(function (e) {
                // alert(JSON.stringify(e));
                if (e.result == SUCCESS) {
                    if (e.data.isLogin) {
                        _this.memberProvider.getMember(e.data.memberId).then( e1 => {
                            if(e1.result == SUCCESS){
                                _this.memberProvider.setLoginMember(e1.data);
                                _this.goBack();
                            }else{
                                _this.nativeProvider.showToastFormI4(e1.message);
                            }
                        },err => _this.nativeProvider.showToastFormI4(err.message))
                    } else {
                        _this.navCtrl.navigateForward(["RegisterPage", {appOpenId:e.data.openid, unionId:e.data.unionid}]);
                    }
                }
            })
        } else{
            if (this.shareData == 'null' || !this.shareData) {
                this.wechatService.getWechatId("weChatLogin");
                return
            }
            this.navCtrl.navigateForward(["RegisterPage", {shareData: this.shareData}]);
        }
    }

    goRegister() {
        if ((this.shareData == 'null' || !this.shareData) && this.appConfig.wechatAutoLogin) {
            this.wechatService.getWechatId("RegisterPage");
            return
        }
        this.navCtrl.navigateForward(["RegisterPage", {shareData: this.shareData}]);
    }

    qqLogin() {
        if (this.appConfig.isApp){
            this.weChatAppService.QQLogin();
        } else{
            alert("非常抱歉，仅支持app上的qq登录")
        }
    }

}
