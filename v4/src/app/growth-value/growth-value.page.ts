import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";
import {MemberProvider} from "../../services/member-service/member";
import {PageQuery} from "../../util/page-query.model";

@Component({
    selector: 'app-growth-value',
    templateUrl: './growth-value.page.html',
    styleUrls: ['./growth-value.page.scss'],
})
export class GrowthValuePage implements OnInit {
    data;
    pageQuery: PageQuery = new PageQuery();

    constructor(public navCtrl: NavController, public nativeProvider: NativeProvider, public memberProvider: MemberProvider) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.pageQuery.addOrFilter('member.id', MemberProvider.getLoginMember().id, 'eq');

        this.memberProvider.queryIntegralRecords(this.pageQuery.requests).then(e => {
            this.data = e.content;
            this.pageQuery.covertResponses(e);
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    /*doRefresh(refresher) {
        this.pageQuery.resetRequests();
        this.memberProvider.queryIntegralRecords(this.pageQuery.requests).then(data => {
                this.data = data.content;
                this.pageQuery.covertResponses(data);
                refresher.target.complete();
            }, err => refresher.target.complete());
    }*/

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.memberProvider.queryIntegralRecords(this.pageQuery.requests).then(data => {
                    this.data = this.data.concat(data.content);
                    this.pageQuery.covertResponses(data);
                    infiniteScroll.target.complete();
                }, err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

}
