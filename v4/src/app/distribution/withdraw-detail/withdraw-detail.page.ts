import { Component, OnInit } from '@angular/core';
import {AccountRecord} from "../../../domain/original/account-record.model";
import {PageQuery} from "../../../util/page-query.model";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";
import {NavController} from "@ionic/angular";
import {TRADE_TYPE} from "../../Constants";

@Component({
  selector: 'app-withdraw-detail',
  templateUrl: './withdraw-detail.page.html',
  styleUrls: ['./withdraw-detail.page.scss'],
})
export class WithdrawDetailPage {
    accountRecord: AccountRecord[] = [];
    isLoading: boolean = false;
    pageQuery: PageQuery = new PageQuery();

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController) {
    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.pageQuery.resetRequests();
        this.pageQuery.addFilter('tradeType', 3, 'eq');
        this.getData(this.pageQuery);
    }

    private getData(page: PageQuery) {
        this.isLoading = true;
        this.memberProvider.getAccountRecord(page.requests).then(data => {
            for (let i = 0; i < data.content.length; i++) {
                data.content[i].tradeType =  TRADE_TYPE[this.accountRecord[i].tradeType]
            }
            this.accountRecord = data.content;
            this.pageQuery.covertResponses(data);
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
