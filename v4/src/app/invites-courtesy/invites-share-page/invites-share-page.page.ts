import {Component, OnInit, ViewChild} from '@angular/core';
import {ModalController, NavParams} from '@ionic/angular';
import {MemberProvider} from '../../../services/member-service/member';
import {WechatforAppService} from '../../../services/forApp-service/wechat.service';
import {AppConfig} from '../../app.config';
import {qrcanvas} from 'qrcanvas';
import {Member} from '../../../domain/original/member.model';

@Component({
    selector: 'app-invites-share-page',
    templateUrl: './invites-share-page.page.html',
    styleUrls: ['./invites-share-page.page.scss'],
})
export class InvitesSharePagePage implements OnInit {

    @ViewChild('canvas') public canvas;

    myCode = '../../../assets/loading.gif';

    memberId;

    /**
     * 分享信息
     */
    shareLink;

    constructor(public navParam: NavParams,public appConfig: AppConfig, public appSerivce: WechatforAppService, public modalCtrl: ModalController) {
        this.memberId = this.navParam.data.memberId;
    }

    ngOnInit() {

    }

    ionViewWillEnter() {
        this.shareLink = encodeURI(this.appConfig.shareURL + 'wechatShare.html?InviteRegisterPage&preMemberId=' + this.memberId + '?');
        this.createCode();
    }

    createCode() {
        let canvas = this.canvas.nativeElement;

        let ctx = canvas.getContext('2d');

        const image = new Image();
        image.src = this.appConfig.shareURL + '/assets/imgs/logo-new.png';
        image.width = 5;
        image.height = 5;

        image.setAttribute('crossOrigin', 'Anonymous');

        image.onload = () => {
            const canvasQr = qrcanvas({
                cellSize: 8,
                padding: 16,
                correctLevel: 'H',
                data: this.shareLink,
                background: 'white',
                logo: {
                    image,
                },
            });

            ctx.drawImage(canvasQr, 0, 0, 310, 288);
            this.myCode = canvas.toDataURL('image/jpeg');
        };
    }


    close() {
        this.modalCtrl.dismiss();
    }

}
