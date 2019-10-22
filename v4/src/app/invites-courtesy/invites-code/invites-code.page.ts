import {Component, OnInit, ViewChild} from '@angular/core';
import {qrcanvas} from 'qrcanvas'
import {MemberProvider} from "../../../services/member-service/member";
import {AppConfig} from "../../app.config";
import {WechatforAppService} from "../../../services/forApp-service/wechat.service";
import {ShareClickModalPage} from "../../share-modal/share-click-modal/share-click-modal.page";
import {ModalController} from "@ionic/angular";
import {Member} from '../../../domain/original/member.model';


@Component({
    selector: 'app-invites-code',
    templateUrl: './invites-code.page.html',
    styleUrls: ['./invites-code.page.scss'],
})
export class InvitesCodePage implements OnInit {

    myCode = '../../../assets/loading.gif';
    @ViewChild('canvas') public canvas;
    member;

    /**
     * 分享信息
     */
    shareData = {
        title: '邀请有礼',
        desc: '加入我们的团队,登峰造极',
        link: '',
        imgUrl: '',
    };

    constructor(public appConfig: AppConfig, public appSerivce:WechatforAppService, public modalCtrl: ModalController,) {
        this.member = MemberProvider.getLoginMember();
        this.shareData = {
            title: '邀请有礼',
            desc: '加入我们的团队,登峰造极',
            link: encodeURI(this.appConfig.shareURL + 'wechatShare.html?RegisterPage&preMemberId=' + this.member.id + '?'),
            imgUrl: this.appConfig.shareURL + 'assets/imgs/%E6%9C%8D%E5%8A%A1%E4%B8%AD%E5%BF%83logo.png',
        };
    }

    ngOnInit() {
        this.createCode();
        this.appSerivce.shareDate(this.shareData, false)
    }

    createCode() {
        let canvas = this.canvas.nativeElement;
        let ctx = canvas.getContext('2d');
        ctx.fillStyle = 'rgba(255,245,221,1)';
        ctx.fillRect(0, 0, 442, 454);
        ctx.fillStyle = '#ffffff';
        ctx.fillRect(96, 126, 252, 252);

        ctx.fillStyle = '#892A39';
        ctx.font = "bold 30px PingFang-SC-Bold sans-serif";
        ctx.fillText("邀你加入我的队伍", 100, 50);
        ctx.font = "20px PingFang-SC-Bold sans-serif";
        ctx.fillText("赢取iPhoneX更多好礼", 118, 88);
        ctx.fillText("扫一扫即可领取好礼", 128, 426);

        const canvasQr = qrcanvas({
            size: 206,
            data: this.shareData.link
        });

        ctx.drawImage(canvasQr, 118, 148, 208, 208);
        this.myCode = canvas.toDataURL('image/jpeg');
    }

    shareFriend(){
        if (this.appConfig.isApp) {
            this.appSerivce.share('SESSION', function () {
                return
            }, function (reason) {
                return
            }, this.shareData)
        }else{
            this.openShareModal();
        }
    }

    shareWeChat(){
        if (this.appConfig.isApp) {
            this.appSerivce.share('TIMELINE', function () {
                return
            }, function (reason) {
                return
            }, this.shareData)
        }else{
            this.openShareModal();
        }
    }

    private async openShareModal(){
        const modal = await this.modalCtrl.create({
                component: ShareClickModalPage,
            }
        );
        await modal.present();
    }
}
