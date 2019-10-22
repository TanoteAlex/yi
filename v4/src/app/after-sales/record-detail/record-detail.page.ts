import {Component} from '@angular/core';
import {NativeProvider} from "../../../services/native-service/native";
import {OrderProvider} from "../../../services/order-service/order";
import {NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {ReturnOrder} from "../../../domain/original/return-order.model";
import {ReturnProcess} from "../../../domain/original/return-process";
import {AfterSaleService} from "../../../services/order-service/after-sale.service";

@Component({
    selector: 'app-record-detail',
    templateUrl: './record-detail.page.html',
    styleUrls: ['./record-detail.page.scss'],
})
export class RecordDetailPage {

    returnOrder: ReturnOrder;

    progress = [];

    // returnProcesses: ReturnProcess[] = [];

    // this.navParams.get("order").id
    constructor(public orderProvider: OrderProvider, public nativeProvider: NativeProvider, public navCtrl: NavController, public route: ActivatedRoute,
                public afterSaleProvider: AfterSaleService) {
    }

    ionViewWillEnter() {
        this.afterSaleProvider.getAfterSaleDetail(this.route.params["value"].orderId).then(e => {
            this.returnOrder = e.data;
            // this.returnProcesses = e.data.returnProcesses;

            /*this.progress = [{
                Img: this.PassImg,
                tip: "提交申请",
                date: this.returnOrder.applyTime.substr(0, 10),
                time: this.returnOrder.applyTime.substr(10)
            }, {
                Img: this.StayImg,
                tip: "待定",
                date: "",
                time: ""
            }, {
                Img: this.StayImg,
                tip: "待定",
                date: "",
                time: ""
            }, {
                Img: this.StayImg,
                tip: "待定",
                date: "",
                time: ""
            }, {
                Img: this.StayImg,
                tip: "待定",
                date: "待定",
                time: ""
            }];*/

        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        })
    }


    StayImg = "../../assets/app_icon/customer/步骤待定@2x.png";
    PassImg = "../../assets/app_icon/customer/步骤完成@2x.png";

    goAuditProgress() {
        this.navCtrl.navigateForward(["AuditProgressPage", {orderId: this.route.params["value"].orderId}]);
    }

}
