import {Component, ViewChild,} from '@angular/core';
import {ModalController, NavController, NavParams} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {CouponReceive} from "../../../domain/original/coupon-receive.model";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";
import {TicketComponent} from "../../components/ticket/ticket.component";

@Component({
    selector: 'app-coupon-order',
    templateUrl: './coupon-order.page.html',
    styleUrls: ['./coupon-order.page.scss'],
})
export class CouponOrderPage {
/*
    pet = "canuse";

    couponVo: CouponReceive[];

    /!*子组件字段对应字段*!/
    tickets = [];

    /!*购物车已选优惠券id*!/
    checkedId: number;

    constructor(public params: NavParams, public modalCtrl: ModalController, public route: ActivatedRoute, public nativeProvider: NativeProvider,
                public memberProvider: MemberProvider, public navCtrl: NavController) {

        if (this.params.data.coupon) {
            this.checkedId = this.params.data.coupon.id
        }

    }

    ionViewWillEnter() {
            this.couponVo =  this.params.data.couponList;

            this.tickets = this.couponVo.map(e => {
                return {
                    id: e.id,
                    state: ["", "coupon-canuse", "", "", "coupon-unuse"][e.state],
                    parValue: e.coupon.parValue,
                    useConditionType: e.coupon.useConditionType,
                    fullMoney: e.coupon.fullMoney,
                    fullQuantity: e.coupon.fullQuantity,
                    platform: "",
                    endTime: e.endTime,
                    couponType: e.coupon.couponType
                }
            });
            this.ordersFilter("canuse");
    }

    list = [];

    ordersFilter(type) {
        switch (type) {
            case "canuse":
                this.list = this.tickets.filter(e =>
                    e.state == "coupon-canuse"
                );
                break;
            case "unuse":
                this.list = this.tickets.filter(e =>
                    e.state == "coupon-unuse"
                );
                break;
            default:
                break;
        }
    }

    /!*获取子组件选中的券*!/
    @ViewChild(TicketComponent) childTicket: TicketComponent;
    selectedCoupon;

    goWriteOrder() {
        if (this.childTicket) {
            /!*cancel即ticket组件取消check选择返回，后面为组件ticket选中返回的正确优惠券，nav则是没有任何操作由返回原本的优惠券*!/
            // this.selectedCoupon = (this.childTicket.selectedCoupon == "cancel" ? undefined : (this.childTicket.selectedCoupon || this.params.data.coupon));
        }
        if (this.selectedCoupon) {
            this.modalCtrl.dismiss(this.selectedCoupon);
        } else {
            this.modalCtrl.dismiss();
        }
    }*/

}
