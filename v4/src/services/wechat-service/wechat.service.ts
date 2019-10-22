import {Injectable} from '@angular/core';
import {HttpServiceProvider} from "../http-service/http-service";
import {AppConfig} from "../../app/app.config";
import {AlertController} from "@ionic/angular";
import {HttpClient, HttpParams} from "@angular/common/http";
import {ObjectUtils} from '../../util/object-utils';

declare let wx;
declare let QC;


@Injectable()
export class WechatService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "weChat");
    }

    /**
     * 微信公众号授权
     */
    authWechat() {
        let url = window.location.href.split('#')[0], params = new HttpParams().set('url', url);
        this.get('getJsapiAuthForSp', params).then(data1 => {
            let data = data1.data;
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appId,//'wx624636d7fc542eb3', // 必填，公众号的唯一标识
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.nonceStr, // 必填，生成签名的随机串
                signature: data.signature,// 必填，签名
                jsApiList: ['chooseWXPay', 'updateAppMessageShareData', 'updateTimelineShareData', 'onMenuShareAppMessage', 'onMenuShareTimeline', 'onMenuShareQQ', 'onMenuShareQZone'] // 必填，需要使用的JS接口列表
            });
        })
        // updateAppMessageShareData分享给朋友 qq ,updateTimelineShareData 分享到朋友圈”及“分享到QQ空间,onMenuShareTimeline 分享到朋友圈
        wx.checkJsApi({
            jsApiList: ['chooseWXPay', 'updateAppMessageShareData', 'updateTimelineShareData', 'onMenuShareAppMessage', 'onMenuShareTimeline', 'onMenuShareQQ', 'onMenuShareQZone'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function (res) {
                // 以键值对的形式返回，可用的api值true，不可用为false
                // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
                // alert(JSON.stringify(res))
                console.log('checkJsApi success');
            },
            fail: function (res) {
                console.log('checkJsApi fail');
                // alert(JSON.stringify(res))
            }
        });

    }

    /**
     * 微信公众号分享
     * @param shareData
     */
    share(shareData) {
        if (!shareData) {
            shareData = {
                title: '测试',
                desc: '分享微信朋友',
                link: encodeURI(this.appConfig.baseURL + '%23/tabs/(customerCenter:customerCenter)'),
                imgUrl: '',
            };

        }
        this.authWechat();

        wx.ready(function () {

            if (wx.onMenuShareAppMessage) { //微信文档中提到这两个接口即将弃用，故判断
                wx.onMenuShareAppMessage(shareData);//1.0 分享到朋友
                wx.onMenuShareTimeline(shareData);//1.0分享到朋友圈
                wx.onMenuShareQQ(shareData);
                wx.onMenuShareQZone(shareData);
            } else {
                wx.updateAppMessageShareData(shareData);//1.4 分享到朋友
                wx.updateTimelineShareData(shareData);//1.4分享到朋友圈
            }
            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

        });


        wx.error(function (res) {
            // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
            console.log(res);
            // alert(" config信息验证失败")
            alert(JSON.stringify(res))
            this.authWechat();
        });
    }

    /**
     * 获取微信unionid，openid
     * content页面，一般为CommodityPage,等
     * contentId,一般为 id=122,具体参照页面，
     * preMemberId，主要为上级用户Id，传入参数需要为preMemberId=18,
     */
    getWechatId(content?, contentId?, preMemberId?, extra?) {
        let config = {
            appid: this.appConfig.wechatAppId,
            redirect_uri: window.location.href.split('#')[0] + 'wechatCode.html',
            // redirect_uri: 'http://test.h5.my11mall.com/'+'wechatCode.html',
            response_type: 'code',
            scope: 'snsapi_userinfo',
            state: 'STATE'
        };

        if (arguments) {
            for (let i = 0; i < arguments.length; i++) {
                if(ObjectUtils.isNotEmpty(arguments[i])){
                    config.redirect_uri += ";" + arguments[i];
                }
            }
        }

        config.redirect_uri += ';debug=0';
        window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${config.appid}&redirect_uri=${config.redirect_uri}&response_type=${config.response_type}&scope=${config.scope}&state=${config.state}#wechat_redirect`
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx624636d7fc542eb…home)&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect.
        //snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
        // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx624636d7fc542eb3&redirect_uri=http://test.h5.my11mall.com/wechatCode.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect 若提示“该链接无法访问”，请检查参数是否填写错误，是否拥有scope参数对应的授权作用域权限。
    }

    /**
     * 微信公众号支付
     * @param data
     * @param sucfun
     * @param failfun
     * @param cancelfun
     */
    pay(data, sucfun?, failfun?, cancelfun?) {
        let defaultSucFun = (res) => {
            alert(JSON.stringify(res))
        };
        let defaultFailfun = (res) => {
            alert(JSON.stringify(res))
        };
        let defaultCancelfun = (res) => {
            alert(JSON.stringify(res))
        };
        if (sucfun instanceof Function) {
            defaultSucFun = sucfun
        }
        if (failfun instanceof Function) {
            defaultFailfun = failfun
        }
        if (cancelfun instanceof Function) {
            defaultCancelfun = cancelfun
        }
        this.authWechat();
        let _this1 = this;
        wx.ready(function () {
            wx.chooseWXPay({
                appId: _this1.appConfig.wechatAppId,
                timestamp: data.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                nonceStr: data.nonceStr, // 支付签名随机串，不长于 32 位
                package: data.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
                signType: data.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                paySign: data.paySign, // 支付签名
                success: defaultSucFun,
                fail: defaultFailfun,
                cancel: defaultCancelfun,
            });
        });
        //prepay_id 通过微信支付统一下单接口拿到，paySign 采用统一的微信支付 Sign 签名生成方法，注意这里 appId 也要参与签名，appId 与 config 中传入的 appId 一致，即最后参与签名的参数有appId, timeStamp, nonceStr, package, signType。
    }

    /**
     * 微信公众号预支付订单
     * @param weChatVo
     * @returns {Promise<any>}
     */
    readyPay(weChatVo) {
        return this.post("getUnifiedOrderForSp", weChatVo);
    }

    /**
     * qq网页登陆
     * @param {string} qqLoginBtnId     qq登陆按钮标签的id
     */
    qqLogin(qqLoginBtnId = 'qqLoginBtn') {
        QC.Login({
            btnId: qqLoginBtnId	//插入按钮的节点id
        });
    }

    cashWithdrawalForMp(money, memberId) {
        const params = new HttpParams().set("money", money).set("memberId", memberId);
        return this.get("cashWithdrawalForSp", params);
    }

    /**
     * 商品分享二维码海报
     */
    getShareImg(url, commodityId) {
        this.nameSpace = 'share';
        const params = new HttpParams().set("url", url).set("commodityId", commodityId);
        return this.get("getShareImage", params);
    }

}
