import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {Member} from "../../../domain/original/member.model";
import {PageQuery} from "../../../util/page-query.model";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-my-team',
    templateUrl: './my-team.page.html',
    styleUrls: ['./my-team.page.scss'],
})
export class MyTeamPage implements OnInit {
    level = 'memberLv1';
    memberList: Member[] = [];
    isLoading: boolean = false;
    pageQuery: PageQuery = new PageQuery();
    /**
     * 请求名,getMemberLv1,getMemberLv2，几重更改之后，暂时不改动原有结构，请求名无用。
     */
    requestName = 'queryCommunityMember';

    /**
     * 显示一级下级人数，
     */
    num1 = 0;

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController,public route: ActivatedRoute, ) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('community.id', this.route.params["value"].communityId);
        this.listFilter(this.level);
    }

    listFilter(type) {
        this.memberList = [];
        this.pageQuery.resetRequests();
        this.pageQuery.addSort('createTime', 'desc');
        if (type == 'memberLv1') this.requestName = 'queryCommunityMember';
        this.getData();
    }

    private getData() {
        this.isLoading = true;
        this.memberProvider[this.requestName](this.pageQuery.requests).then(data => {
            this.memberList = data.content;
            this.pageQuery.covertResponses(data);
            if (this.requestName == 'queryCommunityMember') this.num1 = this.pageQuery.responses.totalElements;
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.memberProvider[this.requestName](this.pageQuery.requests).then(data => {
                    this.memberList = this.memberList.concat(data.content);
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
