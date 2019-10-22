import {Component, OnInit} from '@angular/core';
import {ModalController, NavController} from '@ionic/angular';
import {Member} from '../../../domain/original/member.model';
import {NativeProvider} from '../../../services/native-service/native';
import {MemberProvider} from '../../../services/member-service/member';
import {SUCCESS} from '../../Constants';
import {ShareClickModalPage} from '../../share-modal/share-click-modal/share-click-modal.page';
import {WechatforAppService} from '../../../services/forApp-service/wechat.service';
import {AppConfig} from '../../app.config';
import {InvitesSharePagePage} from '../invites-share-page/invites-share-page.page';
import {InviteActivityProvider} from '../../../services/activity-service/inviteActivity';
import {InviteActivityVo} from '../../../domain/vo/invite-activity-vo.model';
import {InvitePrizeProvider} from '../../../services/activity-service/invite-prize';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {WechatService} from '../../../services/wechat-service/wechat.service';
import {ObjectUtils} from '../../../util/object-utils';

@Component({
    selector: 'app-invites-home',
    templateUrl: './invites-home.page.html',
    styleUrls: ['./invites-home.page.scss']
})
export class InvitesHomePage implements OnInit {

    invitesNum = 0;
    maxNum = 0;
    member: Member;
    /**
     * 分享信息
     */
    shareData = {
        title: '邀请有礼',
        desc: '加入我们的团队,登峰造极',
        link: '',
        imgUrl: '',
    };

    inviteActivity: InviteActivityVo;

    preMemberId;

    curMemberId;

    constructor(public route: ActivatedRoute, public navCtrl: NavController, public memberProvider: MemberProvider, public nativeProvider: NativeProvider,
                public appConfig: AppConfig, public appSerivce: WechatforAppService, public modalCtrl: ModalController, public wechatService: WechatService,
                public invitePrizeProvider: InvitePrizeProvider, public inviteActivityProvider: InviteActivityProvider) {

    }

    ngOnInit() {

        //通过邀请，在链接获取数据
        let href = window.location.href;

        if (!MemberProvider.isLogin() && href.indexOf('unionId=') != -1) {
            let openId = href.split('openId=')[1].split('&')[0];
            let unionId = href.split('unionId=')[1];
            if (href.indexOf('preMemberId') != -1) {
                this.preMemberId = href.split('preMemberId=')[1].split('?')[0];
            }
            this.memberProvider.getMemberByWeChatForSp(unionId, openId).then(e => {
                if (e.result == SUCCESS) {
                    this.memberProvider.setLoginMember(e.data);
                    this.member = e.data;
                    this.preMemberId = e.data.id;
                    this.getActivityInfo(e.data.id);
                    this.nativeProvider.showToastFormI4('欢迎回来，' + e.data.nickname);
                } else {
                    this.memberProvider.loginOut();
                }
            });
        }

        // if(MemberProvider.isLogin()){
        //     let member = MemberProvider.getLoginMember();
        //     if(member.unionId){
        //         this.memberProvider.getMember(member.id).then(e => {
        //             if (e.result == SUCCESS) {
        //                 this.memberProvider.setLoginMember(e.data);
        //                 this.member = e.data;
        //                 this.curMemberId =  e.data.id;
        //                 this.nativeProvider.showToastFormI4('欢迎回来，' + e.data.nickname+'!')
        //             }else{
        //                 this.memberProvider.loginOut();
        //             }
        //         })
        //     }else{ //没授权自动授权
        //         if(href.indexOf('unionId=') != -1){
        //             this.memberProvider.blindWeChat(member.id,href.split('?')[1].split('&')[1].replace('unionId=',''), href.split('?')[1].split('&')[0].replace('openId=',''))
        //         }else{
        //             this.wechatService.getWechatId('HomePage');
        //         }
        //     }
        // }

    }

    ionViewWillEnter() {
        this.route.params.subscribe((params: ParamMap) => {
            this.preMemberId = params['preMemberId'];
            this.getActivityInfo(this.preMemberId);
        });

        this.shareData = {
            title: '邀请有礼',
            desc: '加入我们的团队,登峰造极',
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?InviteRegisterPage&preMemberId=' + this.preMemberId + '?'),
            imgUrl: this.appConfig.shareURL + 'assets/imgs/logo-new.png',
        };
        this.wechatService.share(this.shareData);
    }

    getActivityInfo(memberId) {
        if (ObjectUtils.isNotEmpty(memberId)) {
            this.nativeProvider.showLoadingForI4().then(() => {
                this.inviteActivityProvider.getLatestInviteActivity(memberId).then(data => {

                    this.nativeProvider.hideLoadingForI4();
                    if (data.result == SUCCESS) {
                        this.inviteActivity = data.data;
                    } else {
                        this.nativeProvider.showToastFormI4(data.message);
                    }
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    this.nativeProvider.hideLoadingForI4();
                });
            });
        }
    }

    goInvitedMember() {
        this.navCtrl.navigateForward("InvitedMemberPage");
    }

    goHome() {
        this.navCtrl.navigateRoot('/app/tabs/home', false,);
    }

    goInvitesReceivePrize(prize) {
        const prizeId=prize.id;
        const prizeType=prize.prizeType;
        switch (prizeType) {
            case 1:
                this.invitePrizeProvider.receiveIntegral(prizeId, this.preMemberId).then(e => {
                    if (e.result == SUCCESS) {
                        this.nativeProvider.showToastFormI4('领取成功！');
                        prize.received=true;
                        this.getActivityInfo(this.preMemberId)
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => this.nativeProvider.showToastFormI4(err.message));
                break;
            case 2:
                this.navCtrl.navigateForward(['InvitesReceivePrizePage', {id: prizeId}]);
                break;
            case 3:
                this.invitePrizeProvider.receiveCoupon(prizeId, this.preMemberId).then(e => {
                    if (e.result == SUCCESS) {
                        this.nativeProvider.showToastFormI4('领取成功！');
                        prize.received=true;
                        this.getActivityInfo(this.preMemberId)
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => this.nativeProvider.showToastFormI4(err.message));
                break;
        }
    }

    receivedPrize(){
        this.nativeProvider.showToastFormI4('您已领取过礼品了！');
    }

    notReceivePrize() {
        this.nativeProvider.showToastFormI4('对不起，您还未达到领取条件！');
    }

    getPercentNum(invitesNum, maxNum) {
        return maxNum <= invitesNum ? '100%' : this.invitesNum / this.maxNum * 100 + '%';
    }


    shareFriend() {
        if (this.appConfig.isApp) {
            this.appSerivce.share('SESSION', function () {
                return;
            }, function (reason) {
                return;
            }, this.shareData);
        } else {
            this.openShareModal();
        }
    }

    shareWeChat() {
        if (this.appConfig.isApp) {
            this.appSerivce.share('TIMELINE', function () {
                return;
            }, function (reason) {
                return;
            }, this.shareData);
        } else {
            this.openShareModal();
        }

    }

    async shareQRcode() {
        const modal = await this.modalCtrl.create({
                component: InvitesSharePagePage,
                componentProps: {memberId: this.preMemberId}
            }
        );
        await modal.present();
    }

    private async openShareModal() {
        const modal = await this.modalCtrl.create({
                component: ShareClickModalPage,
            }
        );
        await modal.present();
        // this.wechatService.share(this.shareData);
    }


}
