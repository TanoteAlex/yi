import { Component, OnInit } from '@angular/core';
import {PageQuery} from "../../../util/page-query.model";
import {MemberProvider} from "../../../services/member-service/member";
import {NativeProvider} from "../../../services/native-service/native";

@Component({
  selector: 'app-commission-order',
  templateUrl: './commission-order.page.html',
  styleUrls: ['./commission-order.page.scss'],
})
export class CommissionOrderPage implements OnInit {
    list = [];

    isLoading: boolean = false;
    pageQuery: PageQuery = new PageQuery();

    constructor(public memberProvider: MemberProvider, public nativeProvider: NativeProvider,) {
    }

    ionViewWillEnter() {
        this.pageQuery.addFilter('member.id', MemberProvider.getLoginMember().id, 'eq');
        this.getData(this.pageQuery);
    }

    ngOnInit() {
    }

    private getData(page: PageQuery) {
        this.isLoading = true;
        this.memberProvider.queryMyCommission(page.requests).then(data => {
            this.list = data.content;
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
                    this.list = this.list.concat(data.content);
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
