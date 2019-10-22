import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";
import {PageQuery} from "../../../util/page-query.model";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";

const DEFAULTAVATER = "../../assets/app_icon/new_commodity/group_member_null@2x.png";

// const DEFAULTAVATER="../../assets/app_icon/new_customer/null_icon.png";


@Component({
    selector: 'app-national-group-purchase',
    templateUrl: './national-group-purchase.page.html',
    styleUrls: ['./national-group-purchase.page.scss'],
})
export class NationalGroupPurchasePage {

    pageQuery: PageQuery = new PageQuery();

    list = [];

    isLoading: boolean = false;

    constructor(public groupActiveProvider: GroupActiveService, public navCtrl: NavController, public nativeProvider: NativeProvider) {
    }

    ionViewWillEnter() {
        this.getData()
    }

    getData() {
        this.list = [];
        this.isLoading = true;
        this.groupActiveProvider.queryOrder(this.pageQuery.requests).then(e => {
            this.list = e.content;

            this.pageQuery.covertResponses(e);
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    goGroupCommodity(activityProducts) {
        this.navCtrl.navigateForward(["CommodityGroupPage", {id: activityProducts.id}])
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.groupActiveProvider.queryOrder(this.pageQuery.requests).then(data => {
                this.list = this.list.concat(data.content);
                this.pageQuery.covertResponses(data);
                infiniteScroll.target.complete();
            }, err => infiniteScroll.target.complete());
        } else {
            infiniteScroll.target.complete();
        }
    }

    getJoinMember(openGroupBuys) {
        if (!openGroupBuys) {
            return;
        }
        let avater = [];
        avater.push(openGroupBuys.member.avater);
        openGroupBuys.joinGroupBuys.forEach(e => {
            avater.push(e.member.avater);
        });
        while (avater.length < openGroupBuys.groupNum) {
            avater.push(DEFAULTAVATER);
        }
        return avater;
    }

    /**
     * orderType 0默认订单 1开团，参团
     * groupType 拼团类型（1.全国拼团 2.小区拼团 3.返现拼团 4.秒杀活动）
     * groupId 拼团配置ID
     * nationalGroupRecordId 0普通订单和开团 id参团时为开团
     */
    goShoppingCart(openGroupBuys) {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward("LoginPage");
            return
        }

        let shoppingCartBo = {
            member: {id: MemberProvider.getLoginMember().id},
            openGroupBuy: {id: openGroupBuys.id},
            groupOrderType: "2",
            groupBuyProduct: {id: openGroupBuys.groupBuyActivityProduct.id},
            quantity: "1",
        };

        this.navCtrl.navigateForward(["WriteOrderGroupPage", {data: JSON.stringify(shoppingCartBo), orderType: 'joinGroup'}], false);
    }
}
