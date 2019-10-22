import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from "../../services/native-service/native";
import {AccountRecord} from "../../domain/original/account-record.model";
import {PageQuery} from "../../util/page-query.model";
import {TRADE_TYPE} from "../Constants";

@Component({
    selector: 'app-my-balance',
    templateUrl: './my-balance.page.html',
    styleUrls: ['./my-balance.page.scss'],
})
export class MyBalancePage {
    balance = 0;
    accountRecord: AccountRecord[] = [];
    pageQuery: PageQuery = new PageQuery();

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController) {
    }

    ionViewWillEnter() {
        this.memberProvider.getAccountInfo(MemberProvider.getLoginMember().id).then(e => {
            if (e.result == "SUCCESS") {
                this.balance = e.data.balance;
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });

        this.getData(this.pageQuery);
    }

    private getData(page: PageQuery) {
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.memberProvider.getAccountRecord(page.requests).then(data => {
            this.accountRecord = data.content.slice(0, 4);
            this.pageQuery.covertResponses(data);
            for (let i = 0; i < this.accountRecord.length; i++) {
                this.accountRecord[i].tradeType = TRADE_TYPE[this.accountRecord[i].tradeType]
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    goFineBalance() {
        this.navCtrl.navigateForward("FineBalancePage")
    }
}
