import {Injectable} from "@angular/core";
import {HttpServiceProvider} from "../http-service/http-service";
import {AppConfig} from "../../app/app.config";
import {ActionSheetController, AlertController, ModalController} from "@ionic/angular";
import {HttpClient, HttpParams} from "@angular/common/http";
import {WechatService} from "../wechat-service/wechat.service";
import {SUCCESS} from "../../app/Constants";
import {ShareClickModalPage} from "../../app/share-modal/share-click-modal/share-click-modal.page";
import {QQSDKOriginal, QQShareOptions} from "@ionic-native/qqsdk";
// import { QQSDK, QQShareOptions } from '@ionic-native/qqsdk';

declare let cordova: any;
declare let QQSDK: any;

@Injectable()
export class WechatforAppService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController, public wechatService: WechatService, public sharesheetCtrl: ActionSheetController,
                public modalCtrl: ModalController) {
        super(appConfig, http, alertCtrl, "weChat");
    }

    /**
     * 检查是否安装微信
     * @returns {boolean}
     */
    checkWechat() {
        if (typeof window.Wechat === "undefined") {
            alert("设备上没有安装微信！");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查是否安装qq
     */
    // args = {client:''};
    // args.client = QQSDK.ClientType.QQ;//QQSDK.ClientType.QQ,QQSDK.ClientType.TIM;
    // QQSDK.checkClientInstalled(function () {
    //     alert('client is installed');
    // }, function () {
    // if installed QQ Client version is not supported sso,also will get this error
    //     alert('client is not installed');
    // }, args);

    // checkQQ() {
    //     const loginOptions: QQShareOptions = {
    //         client: QQSDK.ClientType.QQ,
    //     };
    //     QQSDK.checkClientInstalled(loginOptions).then((result) => {
    //         alert(JSON.stringify(result) + '有qq');
    //         return true
    //     }).catch(error => {
    //         alert(JSON.stringify(error) + '没qq');
    //         return false
    //     })
    //
    // }


    /**
     * qq登录
     */
    QQLogin() {
        // if (!this.checkQQ()) {
        // alert("没有qq");
        // }

        console.log("qq进来了");
        const loginOptions: QQShareOptions = {
            client: QQSDK.ClientType.QQ,
        };
        console.log("第一步");
        QQSDK.ssoLogin(loginOptions)
            .then(result => {
                // console.log('shareNews success');
                alert('success');
                // alert('token is ' + result.access_token);
                // alert('userid is ' + result.userid);
            }, err => alert(JSON.stringify(err)))
            .catch(error => {
                alert('error');
                // console.log(error);
            });
        console.log("最后一步");
    }

    /**
     * qq分享
     */


    /**
     * 登陆auth
     * @param onSuccess
     */
    auth(onSuccess) {
        if (this.checkWechat()) {
            var scope = "snsapi_userinfo",
                state = "_" + (+new Date());
            window.Wechat.auth(scope, state, e => {
                // alert("success: " + JSON.stringify(e));
                this.get(`authLoginForApp?code=${e.code}`).then(data => onSuccess(data), err => {
                    // alert(JSON.stringify(err))
                })
            }, reason => {
                // alert("Failed: " + reason);
            });
        }
    }

    /**
     * 微信支付接口
     * @param paymentModel
     */
    weChatPay(paymentModel: any, onSuccess, onError): Promise<any> {
        // console.log(JSON.stringify(paymentModel));
        return this.post('/getUnifiedOrderForApp', JSON.stringify(paymentModel)).then(params => {
            if (params) {
                if (this.checkWechat()) {
                    params = params.data;
                    let param = {
                        appid: params.appid,
                        partnerid: params.partnerid,
                        prepayid: params.prepayid,
                        package: params.package,
                        noncestr: params.noncestr,
                        timestamp: params.timestamp,
                        sign: params.sign,
                    };
                    window.Wechat.sendPaymentRequest(param, onSuccess, onError);
                }
            }
        }, error => {
            console.log('system error');
        });
    }


    /**
     * 微信分享
     * @param shareData
     */

    /*let shareData = {
        title: this.commodity.commodityName,
        desc: this.commodity.commodityShortName,
        link: encodeURI(window.location.href.split('#')[0] + 'wechatShare.html?CommodityPage&id=' + this.commodity.id + '&preMemberId=' + memberId + '?'),
        imgUrl: this.commodity.imgPath,
    };*/
    /**
     *
     * @param shareData 公众号需要先初始化数据，后弹出提示右上角分享.app不需要，直接弹出
     * @param {boolean} isShowModal 是否弹框，提示分享操作
     * @returns {Promise<void>}
     */
    async shareDate(shareData, isShowModal = true) {
        if (this.appConfig.isApp && isShowModal) {
            const shareSheet = await this.sharesheetCtrl.create({
                header: "分享给好友",
                cssClass: "share",
                buttons: [
                    {
                        cssClass: 'weChat',
                        text: '朋友圈',
                        handler: () => {
                            this.share('TIMELINE', function () {
                                return
                            }, function (reason) {
                                return
                            }, shareData)
                        }
                    },
                    {
                        cssClass: 'weChatFriends',
                        text: '微信好友',
                        handler: () => {
                            this.share('SESSION', function () {
                                return
                            }, function (reason) {
                                return
                            }, shareData)
                        }
                    },
                ]
            });
            shareSheet.present();
            /*const modal = await this.modalCtrl.create({
                component: ShareModalPage,
                componentProps: {shareData: shareData},
            });
            await modal.present();*/
        } else {
            if (isShowModal) {
                const modal = await this.modalCtrl.create({
                        component: ShareClickModalPage,
                    }
                );
                await modal.present();
            }
            this.wechatService.share(shareData);
        }
    }


    /**
     *
     * @param type    TIMELINE 朋友圈     SESSION 朋友
     * @param onSuccess
     * @param onError
     * @param shareData
     */
    share(type, onSuccess, onError, shareData?) {
        if (this.checkWechat()) {
            if (!shareData) {
                shareData = {
                    title: '壹壹测试',
                    desc: '分享微信朋友',
                    link: encodeURI('http://test.h5.my11mall.com/#/tabs/(home:home)'),
                    imgUrl: "http://h5.my11mall.com/assets/icon/icon.png",
                };
            }
            window.Wechat.share({
                message: {
                    title: shareData.title,
                    description: shareData.desc,
                    thumb: shareData.imgUrl,
                    media: {
                        type: window.Wechat.Type.WEBPAGE,
                        webpageUrl: shareData.link
                    }
                },
                scene: window.Wechat.Scene[type]   // share to Timeline
            }, onSuccess, onError);
        }
    }


    /**
     * 支付宝支付接口
     * @param paymentModel
     */
    AliPay(paymentModel: any, onSuccess, onError): Promise<any> {
        // alert(JSON.stringify(paymentModel) + "支付宝信息1");
        this.setNameSpace = "alipay";
        return this.post('createPayOrder', JSON.stringify(paymentModel)).then(params => {
            // alert(JSON.stringify(params) + "支付宝信息");
            if (params.result == SUCCESS) {
                cordova.plugins.alipay.payment(params.data, onSuccess, onError);
            } else {
                onError()
            }
        }, error => {
            onError();
        });
    }

}
