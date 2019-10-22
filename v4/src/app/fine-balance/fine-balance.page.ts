import {Component} from '@angular/core';

import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {AccountRecord} from "../../domain/original/account-record.model";
import {NativeProvider} from "../../services/native-service/native";
import {PageQuery} from "../../util/page-query.model";
import {TRADE_TYPE} from "../Constants";

@Component({
    selector: 'app-fine-balance',
    templateUrl: './fine-balance.page.html',
    styleUrls: ['./fine-balance.page.scss'],
})
export class FineBalancePage {
    balance: string = "all";
    accountRecord: AccountRecord[] = [];
    isLoading:boolean = false;
    pageQuery: PageQuery = new PageQuery();

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController) {
    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.listFilter(this.balance);
    }

    listFilter(type) {
        this.pageQuery.resetRequests();
        this.queryFilter(type);
        this.getData(this.pageQuery);
    }

    queryFilter(type) {
        switch (type) {
            case "expenditure":
                this.pageQuery.addOrFilter('operateType', 2, 'eq');
                break;
            case "income":
                this.pageQuery.addOrFilter('operateType', 1, 'eq');
                break;
            default:
                break;
        }
    }

    private getData(page: PageQuery) {
        this.isLoading = true;
        this.memberProvider.getAccountRecord(page.requests).then(data => {
            this.accountRecord = data.content;
            this.pageQuery.covertResponses(data);
            for (let i = 0; i < this.accountRecord.length; i++) {
                this.accountRecord[i].tradeType = TRADE_TYPE[this.accountRecord[i].tradeType]
            }
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.memberProvider.getAccountRecord(this.pageQuery.requests).then(data => {
                    for (let i = 0; i < data.content.length; i++) {
                        data.content[i].tradeType = TRADE_TYPE[data.content[i].tradeType]
                    }
                    this.accountRecord = this.accountRecord.concat(data.content);
                    this.pageQuery.covertResponses(data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

}
