import {Component, Input, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
@Component({
    selector: 'ticket',
    templateUrl: './ticket.component.html',
    styleUrls: ['./ticket.component.scss']
})
export class TicketComponent implements OnInit {
    @Input()
    ticket: any;

    constructor(public navCtrl: NavController) {
    }

    ngOnInit() {
    }

    gotoUse(couponId){
        this.navCtrl.navigateForward(["CommoditiesListPage", {couponId: couponId}])
    }


    /*使用条件提示*/
    getConditionContent(useConditionType, fullMoney, fullQuantity) {
        switch (useConditionType) {
            case 0 :
                return "&nbsp;";
            case 1 :
                return "满" + fullMoney + "元可用";
            case 2 :
                return "满" + fullQuantity + "件可用";
            default:
                return
        }
    }

    /*有效时间提示*/
    getTerm(endTime, fixedDay?) {
        if (endTime) {
            return "有效期至：" + endTime.substr(0, 10);
        }
        if (fixedDay) {
            return "有效天数：" + fixedDay + "天";
        }
    }
}
