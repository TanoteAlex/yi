import {Component, ComponentFactoryResolver, Input, OnInit, SimpleChanges, ViewChild, ViewContainerRef} from '@angular/core';
import {NavController} from "@ionic/angular";
import {COUPON_TYPE} from "../../../Constants";
import {CouponStorageComponent} from "../coupon-storage/coupon-storage.component";
import {CouponComponent} from "../coupon/coupon.component";
import {WechatforAppService} from "../../../../services/forApp-service/wechat.service";
import {AppConfig} from "../../../app.config";

@Component({
    selector: 'show-coupon',
    templateUrl: './show-coupon.component.html',
    styleUrls: ['./show-coupon.component.scss']
})
export class ShowCouponComponent implements OnInit {

    @Input()
    ticket: any;

    @Input()
    sendOthers:any;

    @Input()
    disable:boolean = false;

    @ViewChild('showCoupon', {read: ViewContainerRef}) _showCoupon;

    _controlRef;

    constructor(public navCtrl: NavController, private componentFactoryResolver: ComponentFactoryResolver,public appConfig: AppConfig, public wechatforAppService: WechatforAppService) {
    }

    ngOnInit() {

    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.ticket.currentValue) {
            this._showCoupon.clear();
            if (changes.ticket.currentValue.couponType == COUPON_TYPE[1]) {
                const controlComponentFactory = this.componentFactoryResolver.resolveComponentFactory(CouponComponent);
                this._controlRef = this._showCoupon.createComponent(controlComponentFactory);
            }
            if (changes.ticket.currentValue.couponType == COUPON_TYPE[2]) {
                const controlComponentFactory = this.componentFactoryResolver.resolveComponentFactory(CouponStorageComponent);
                this._controlRef = this._showCoupon.createComponent(controlComponentFactory);
            }
            this._controlRef.instance.ticket = this.ticket;
            this._controlRef.instance.sendOthers = this.sendOthers;
            // this._controlRef.instance.getConditionContent = this.getConditionContent;
            // this._controlRef.instance.getTerm = this.getTerm;
            this._controlRef.instance.gotoUse = this.gotoUse;
            this._controlRef.instance.disable = this.disable;
        }
    }

    /*使用条件提示*/
    // getConditionContent(useConditionType, fullMoney, fullQuantity) {
    //     switch (useConditionType) {
    //         case 0 :
    //             return "&nbsp;";
    //         case 1 :
    //             return "满" + fullMoney + "元可用";
    //         case 2 :
    //             return "满" + fullQuantity + "件可用";
    //         default:
    //             return
    //     }
    // }
    //
    // /*有效时间提示*/
    // getTerm(endTime, fixedDay?) {
    //     if (endTime) {
    //         return endTime.substr(0, 10).replace(/-/g,'.');
    //     }
    //     if (fixedDay) {
    //         return "有效天数" + fixedDay + "天";
    //     }
    // }

    gotoUse(couponId){
        this.navCtrl.navigateForward(["CommoditiesListPage", {couponId: couponId}])
    }

}
