import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";
import {NativeProvider} from "../native-service/native";
import {AlertController, Events} from "@ionic/angular";
import {Storage} from "@capacitor/core";
import {Geolocation} from '@ionic-native/geolocation/ngx'
import {LOCAL_CITY} from "../../app/Constants";
import {Member} from "../../domain/original/member.model";
import {WechatService} from "../wechat-service/wechat.service";
import {ObjectUtils} from '../../util/object-utils';

/*
  Generated class for the MemberProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class MemberProvider extends HttpServiceProvider {

    static LOGIN_MEMBER = "LOGIN_MEMBER";

    static CITY_INFO = 'CITY_INFO';

    private static loginMember: Member;
    private city = '请定位';
    private lockCity = '';

    constructor(public geolocation: Geolocation, private nativeService: NativeProvider, public events: Events, public appConfig: AppConfig, public http: HttpClient,
                public alertCtrl: AlertController, public wechatProvider: WechatService) {
        super(appConfig, http, alertCtrl, "member");
    }

    /**
     * 用户登录
     */

    /*注册*/
    register(newMemberBo) {
        return this.post('register', newMemberBo);
    }

    inviteRegister(memberBo) {
        return this.post('inviteRegister', memberBo);
    }

    /*登录*/
    login(memberBo) {
        let memberBo1 = memberBo;
        memberBo1.loginSource= this.appConfig.isApp ? "app登录" : "公众号";
        return this.post('loginByPassword', memberBo1);
    }

    /*是否已经登录*/
    static isLogin(): boolean {
        return ObjectUtils.isNotEmpty(MemberProvider.loginMember);
    }

    /*注销登录*/
    loginOut() {
        MemberProvider.loginMember = null;
        return Storage.remove({key: MemberProvider.LOGIN_MEMBER})
    }

    /*忘记密码*/
    forgetPassword(memberBo) {
        return this.post('forgetPassword', memberBo);
    }

    /*修改密码*/
    changePwd(memberBo) {
        return this.post('changePwd', memberBo)
    }

    /*修改手机号*/
    changePhone(memberBo) {
        return this.post('updatePhone', memberBo)
    }

    /*设置或修改支付密码*/
    modifyPayPassword(memberBo) {
        return this.post("modifyPayPassword", memberBo)
    }

    /**
     * 更新登录信息
     */

    /*设置登录缓存*/
    setLoginMember(loginMember) {
        Storage.set({key: MemberProvider.LOGIN_MEMBER, value: JSON.stringify(loginMember)});
        MemberProvider.loginMember = loginMember;
    }

    /*初始化缓存*/
    async initLoginSession() {
        let member = await Storage.get({key: MemberProvider.LOGIN_MEMBER});
        MemberProvider.loginMember = JSON.parse(member.value);
    }

    /*获取缓存的用户信息*/
    static getLoginMember() {
        return MemberProvider.loginMember;
    }

    /**
     * 收货地址
     */

    /*请求收货地址信息*/
    getShippingAddress(memberId) {
        const params = new HttpParams().set("memberId", memberId);
        return this.get("getAddress", params)
    }

    /*新增收货地址*/
    addAddress(ShippingAddressBo) {
        return this.post("addAddress", ShippingAddressBo);
    }

    /*设置默认地址*/
    setPreferAddress(memberId, addressId) {
        const params = new HttpParams().set("memberId", memberId).set("addressId", addressId);
        return this.get("setDefaultAddress", params);
    }

    /*删除收货地址*/
    deleteAddress(addressId) {
        const params = new HttpParams().set("addressId", addressId);
        return this.get("removeAddress", params);
    }

    /*编辑收货地址*/
    editAddress(shippingAddressBo) {
        return this.post("changeAddress", shippingAddressBo)
    }

    /*地址详情*/
    getShippingAddressDetail(addressId) {
        const params = new HttpParams().set("addressId", addressId);
        return this.get("getAddressDetail", params);
    }


    /**
     * 用户信息
     */

    /*请求用户信息*/
    getMember(memberId) {
        const params = new HttpParams().set('memberId', memberId);
        return this.get("getMember", params);
    }

    getMemberByWeChatForSp(unionId: string, openId: string) {
        const params = new HttpParams().set('unionId', unionId).set('openId',openId);
        return this.get("getMemberByWeChatForSp", params);
    }

    changMember(memberBo) {
        return this.post("changeMember", memberBo)
    }

    /*个人中心中请求优惠券信息*/
    queryCoupon(queryPage) {
        return this.post("getCoupon", queryPage);
    }

    /*获取会员信息,账户余额,用于查询会员积分（成长值）*/
    getAccountInfo(memberId) {
        const params = new HttpParams().set("memberId", memberId);
        return this.get("getAccountByMemberId", params);
    }

    /*分页查询积分记录*/
    queryIntegralRecords(page) {
        return this.post("queryIntegralRecords", page);
    }

    /*邀请列表*/
    getMyTeamNum(memberId) {
        const params = new HttpParams().set("memberId", memberId);
        return this.get("getMyTeamNum", params);
    }

    /*账单明细*/
    getAccountRecord(page) {
        return this.post("getAccountRecords", page);
    }

    /*根据城市获取小区列表*/
    getCommunityList(cityCode) {
        const params = new HttpParams().set("city", cityCode);
        return this.get("getCommunityByCity", params);
    }

    /*获取小区信息*/
    getCommunityInfo(memberId) {
        const params = new HttpParams().set("memberId", memberId);
        return this.get("getCommunityByMemberId", params);
    }

    /*修改用户小区*/
    updateCommunity(memberId, communityId) {
        const params = new HttpParams().set("memberId", memberId).set("communityId", communityId);
        return this.get("updateCommunity", params);
    }

    /*签到信息*/
    getMemberSign(memberId) {
        const params = new HttpParams().set("memberId", memberId);
        return this.get("memberSign", params);
    }

    /*点击签到*/
    clickSign(memberId) {
        const params = new HttpParams().set("memberId", memberId);
        return this.get("checkIn", params)
    }

    getLocation(noAuto?) {
        try {
            if (BMap != undefined) {
                // if (this.nativeService.isAndroid()) {
                let geolocation = new BMap.Geolocation();
                // 开启SDK辅助定位
                geolocation.enableSDKLocation();
                geolocation.getCurrentPosition(r => {
                    let myGeo = new BMap.Geocoder();
                    // 根据坐标得到地址描述
                    myGeo.getLocation(new BMap.Point(r.point.lng, r.point.lat), result => {
                        if (result) {
                            this.setLocation(result.addressComponents.province, noAuto)
                        }
                    });
                });
                // }
                if (this.nativeService.isIos()) {
                    this.geolocation.getCurrentPosition().then((resp) => {
                        let myGeo = new BMap.Geocoder();
                        // 根据坐标得到地址描述
                        myGeo.getLocation(new BMap.Point(resp.coords.longitude, resp.coords.latitude), result => {
                            if (result) {
                                this.setLocation(result.addressComponents.province, noAuto)
                            }
                        });
                    }, (error) => {
                        console.log('Error getting location', error);
                    });
                }
            }
        } catch (e) {
            console.log(e);
        }
    }

    setLocation(city, noAuto?) {
        if (noAuto != undefined && noAuto) {
            this.lockCity = city
        } else {
            this.city = city;
        }
        Storage.set({key: MemberProvider.CITY_INFO, value: city}).then(e => this.events.publish(LOCAL_CITY));
    }

    getLocationInfo() {
        return this.lockCity != '' ? this.lockCity : this.city;
    }

    getGpsLocation() {
        return this.city;
    }


    /**
     * 自动登录
     */
    autoLogin(href) {
        /**
         * 该用户信息有unionId，没有则通过href的openid,unionId更新用户信息
         */
        // let href = window.location.href;
        // alert(href);

        if(!this.appConfig.wechatAutoLogin){
            return
        }
        if(!MemberProvider.isLogin() && href.indexOf('unionId=') == -1) {
            this.wechatProvider.getWechatId('HomePage');
        }
        if(!MemberProvider.isLogin() && href.indexOf('unionId=') != -1) {
            this.getMemberByWeChatForSp(href.split('?')[1].split('&')[1].replace('unionId=',''), href.split('?')[1].split('&')[0].replace('openId=','')).then(e => {
                if (e.result == 'SUCCESS') {
                    this.setLoginMember(e.data);
                    this.nativeService.showToastFormI4('欢迎回来，' + e.data.nickname)
                } else {
                    this.loginOut();
                }
            })
        }
        if(MemberProvider.isLogin()){
            let member = MemberProvider.getLoginMember();
            if(member.unionId){
                this.getMember(member.id).then(e => {
                    if (e.result == "SUCCESS") {
                        this.setLoginMember(e.data);
                        this.nativeService.showToastFormI4('欢迎回来，' + e.data.nickname)
                    }else{
                        this.loginOut();
                    }
                })
            }else{ //没授权自动授权
                if(href.indexOf('unionId=') != -1){
                    this.blindWeChat(member.id,href.split('?')[1].split('&')[1].replace('unionId=',''), href.split('?')[1].split('&')[0].replace('openId=',''))
                }else{
                    this.wechatProvider.getWechatId('HomePage');
                }
            }
        }

        /*if (MemberProvider.isLogin()) {
            let member = MemberProvider.getLoginMember();
            //该用户没有openId，也没有通过中转站获取openId的情况。重新请求，确认是否openid。都没有则去中转站获取openid。再回来进行
            // if (!member.unionId && href.indexOf('unionId=') == -1) {
                this.getMember(member.id).then(e => {
                    if (e.result == "SUCCESS") {
                        this.setLoginMember(e.data);
            //             if (!e.data.unionId) {
            //                 this.wechatProvider.login(e.data.id);
                            // this.weChatConfirm(MemberProvider.getLoginMember().id);
                        // }
                    }
                })
            // }//用户没有openId，通过中转站后，回来获取用户。。同时意味着没有openid的用户无法自动登录，会自动登录为绑定的账号
            // else if(!member.unionId && href.indexOf('unionId=') != -1){
            //     this.getMember(member.id).then(e => {
            //         if (e.result == 'SUCCESS') {
            //             this.setLoginMember(e.data);
            //             this.nativeService.showToastFormI4('欢迎回来，' + e.data.nickname)
            //         } else {
            //             this.nativeService.showToastFormI4('授权登陆失败')
            //         }
            //     })
            // }//该用户有openId，更新数据
            // else if (member.unionId) {
            //     this.getMemberByWeChatForMp(member.unionId,member.openId).then(e => {
            //        if (e.result == 'SUCCESS') {
            //             this.setLoginMember(e.data);
            //         } else {
            //             this.nativeService.showToastFormI4('授权登陆失败')
            //         }
            //     })
            // }
        } //自动登录，点击微信登录时
        else if (!MemberProvider.isLogin() && href.indexOf('unionId=') != -1) {
            this.getMemberByWeChatForMp(href.split('?')[1].split('&')[1].replace('unionId=',''), href.split('?')[1].split('&')[0].replace('openId=','')).then(e => {
                if (e.result == 'SUCCESS') {
                    this.setLoginMember(e.data);
                    this.nativeService.showToastFormI4('欢迎回来，' + e.data.nickname)
                } else {
                    console.log("授权失败");
                    return
                }
            })
        }*/
    }

    /**
     * 积分设置信息
     * @param taskType 1签到，2邀请好友，3评论
     * @returns {Promise<any>}
     */
    getIntegralRule(taskType){
        const params = new HttpParams().set("taskType", taskType);
        return this.get("getIntegralTask", params);
    }

    /**
     * 绑定用户与微信号
     */
    blindWeChat(memberId,unionId,openId) {
        const params = new HttpParams().set("memberId", memberId).set("unionId", unionId).set("openId", openId);
        return this.get("bindWeChatForSp", params);
    }

    /**
     * 一二级下级代理查询，分销这一块，后来需求变动，暂时全都没用上。
     */
    getMemberLv1(page) {
        return this.post("queryFirstLevelTeam", page);
    }

    getMemberLv2(page) {
        return this.post("querySecondLevelTeam", page);
    }

    /* 分销佣金明细列表*/
    queryMyCommission(page) {
        return this.post("queryMyCommission", page);
    }

    /* 查询小区业绩*/
    queryCommunityPerformance(page) {
        return this.post("queryCommunityPerformance", page);
    }

    /* 查询小区成员 */
    queryCommunityMember(page) {
        return this.post("queryCommunityMember", page);
    }

    /* 提现至余额 */
    withdrawCommission(memberId,withdrawAmount) {
        const params = new HttpParams().set("memberId", memberId).set("withdrawAmount", withdrawAmount);
        return this.get("withdrawCommission", params);
    }

    /**
     * 邀请有礼新规则
     */
    getInviteNum(memberId){
        const params = new HttpParams().set("memberId", memberId);
        return this.get("getInviteNum", params)
    }

    getInviteOrderAndMember(memberId){
        const params = new HttpParams().set("memberId", memberId);
        return this.get("getInviteOrderAndMember", params)
    }
}
