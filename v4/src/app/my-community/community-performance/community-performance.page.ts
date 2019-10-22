import {Component, OnInit} from '@angular/core';
import {PageQuery} from "../../../util/page-query.model";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";
import {NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-community-performance',
    templateUrl: './community-performance.page.html',
    styleUrls: ['./community-performance.page.scss'],
})
export class CommunityPerformancePage implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    isLoading: boolean = false;
    data;

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public route: ActivatedRoute,) {
    }

    ngOnInit() {

    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('community.id', this.route.params["value"].communityId);
        this.getData();
    }

    private getData() {
        this.isLoading = true;
        this.memberProvider.queryCommunityPerformance(this.pageQuery.requests).then(e => {
            this.data = e.content;
            this.pageQuery.covertResponses(e);
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.memberProvider.queryCommunityPerformance(this.pageQuery.requests).then(data => {
                    this.data = this.data.concat(data.content);
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
