import {Component} from '@angular/core';
import {OrderProvider} from "../../../services/order-service/order";
import {NavController} from "@ionic/angular";
import {ReturnProcess} from "../../../domain/original/return-process";
import {ActivatedRoute} from "@angular/router";
import {ReturnOrder} from "../../../domain/original/return-order.model";
import {NativeProvider} from "../../../services/native-service/native";
import {AfterSaleService} from "../../../services/order-service/after-sale.service";

@Component({
    selector: 'app-audit-progress',
    templateUrl: './audit-progress.page.html',
    styleUrls: ['./audit-progress.page.scss'],
})
export class AuditProgressPage {
    returnOrder: ReturnOrder;

    returnProcesses: ReturnProcess[] = [];

    progress = [];

    /*最新的消息content*/
    // newContent;

    constructor(public nativeProvider: NativeProvider, public orderProvider: OrderProvider, public route: ActivatedRoute, public navCtrl: NavController,
                public afterSaleProvider: AfterSaleService) {

    }

    ionViewWillEnter() {
        this.afterSaleProvider.getAfterSaleDetail(this.route.params["value"].orderId).then(e => {
            if (e.result == "SUCCESS") {
                this.returnOrder = e.data;
                this.progress = this.returnOrder.afterSaleProcesses.map(e => {
                    return {
                        content: e.processInfo,
                        username: e.processPerson,
                        date: e.processDate,
                    }
                });
                this.progress = this.progress.sort((e1, e2) => e1.processDate > e2.processDate ? -1 : 1);
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }
}
