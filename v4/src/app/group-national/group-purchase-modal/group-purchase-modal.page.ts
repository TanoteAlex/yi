import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";
import {AttrGroupModalPage} from "../attr-group-modal/attr-group-modal.page";
import {PageQuery} from "../../../util/page-query.model";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";
import {SUCCESS} from "../../Constants";
import {NativeProvider} from "../../../services/native-service/native";

@Component({
    selector: 'app-group-purchase-modal',
    templateUrl: './group-purchase-modal.page.html',
    styleUrls: ['./group-purchase-modal.page.scss'],
})
export class GroupPurchaseModalPage implements OnInit {

    groupInfo;

    groupMember = [];

    constructor(public modalCtrl: ModalController, public navParam: NavParams, public groupActiveProvider: GroupActiveService, public nativeProvider: NativeProvider) {
        this.groupInfo = this.navParam.data;
        this.getData();
    }

    ngOnInit() {

    }

    getData() {
        this.groupActiveProvider.getOpenGroups(this.groupInfo.id).then(e => {
            this.groupMember = e;
        })
    }

    async joinGroup(item) {
        this.modalCtrl.dismiss();

        /*拼团活动*/
        const modal = await this.modalCtrl.create({
            component: AttrGroupModalPage,
            componentProps: {data: this.groupInfo, groupId: item.id, orderType: 'joinGroup'}
        });
        await modal.present()
    }

}
