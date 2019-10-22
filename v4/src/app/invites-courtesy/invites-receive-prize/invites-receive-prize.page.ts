import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {InvitePrizeVo} from '../../../domain/vo/invite-prize-vo';
import {ChooseConsigneePage} from '../../choose-consignee/choose-consignee.page';
import {ConsigneeModalPage} from '../../consignee-modal/consignee-modal.page';
import {ModalController} from '@ionic/angular';
import {ShippingAddress} from '../../../domain/original/shipping-address.model';
import {MemberProvider} from '../../../services/member-service/member';
import {NativeProvider} from '../../../services/native-service/native';
import {ShippingAddressVo} from '../../../domain/vo/shipping-address-vo.model';
import {InvitePrizeProvider} from '../../../services/activity-service/invite-prize';
import {SUCCESS} from '../../Constants';

@Component({
    selector: 'app-invites-receive-prize',
    templateUrl: './invites-receive-prize.page.html',
    styleUrls: ['./invites-receive-prize.page.scss'],
    providers: [MemberProvider]
})
export class InvitesReceivePrizePage implements OnInit {

    invitePrize: InvitePrizeVo;

    shippingAddress: ShippingAddressVo;

    shippingAddresses: Array<ShippingAddressVo>;

    received: boolean = false;

    constructor(public route: ActivatedRoute, public nativeProvider: NativeProvider, public modalCtrl: ModalController,
                public memberProvider: MemberProvider, public invitePrizeProvider: InvitePrizeProvider) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.route.data.subscribe((data) => {
            this.invitePrize = data.data;
        });
    }

    receive() {
        this.goChooseConsignee();
    }

    async goChooseConsignee() {
        const modalChooseConsignee = await this.modalCtrl.create({
            component: ChooseConsigneePage,
            componentProps: {shippingAddress: this.shippingAddress},
        });
        await modalChooseConsignee.present();
        await modalChooseConsignee.onDidDismiss().then(data => {
            if (data.data != undefined && this.shippingAddress != data.data) {
                this.shippingAddress = data.data;
                this.invitePrizeProvider.receiveCommodity(this.invitePrize.id, MemberProvider.getLoginMember().id, this.shippingAddress).then(e => {
                    if (e.result == SUCCESS) {
                        this.nativeProvider.showToastFormI4('领取成功！');
                        this.received = true;
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                });
            }
        });

    }
}
