
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SUCCESS } from '../../../models/constants.model';
import {PageQuery} from '../../../models/page-query.model';
import { InviteActivityService } from '../../../services/invite-activity.service';
import { InviteActivityListVo } from '../../../models/domain/listVo/invite-activity-list-vo.model';

@Component({
    selector: 'list-invite-activity',
    templateUrl: './list-invite-activity.component.html',
    styleUrls: ['./list-invite-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListInviteActivityComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    inviteActivitys: Array<InviteActivityListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private inviteActivityService: InviteActivityService, public msg: NzMessageService,
        private fb: FormBuilder,private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        title:[null],
        banner:[null],
        rule:[null],
        startTime:[null],
        endTime:[null],
        inviteFlag:[null],
        state:[null],
        deleted:[null],
        delTime:[null],
        });
    }

    sort(sort: { key: string, value: string }): void {
        this.pageQuery.addSort(sort.key,sort.value)
        this.searchData();
    }

    searchData(reset: boolean = false): void {
        if (reset) {
            this.pageQuery.resetPage();
        }
        this.configPageQuery(this.pageQuery);
        this.getData();
    }

    getData() {
        this.loading = true;
        this.inviteActivityService.query(this.pageQuery).subscribe(response => {
            this.inviteActivitys=response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误'+ error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
        id:null,
        guid:null,
        title:null,
        banner:null,
        rule:null,
        startTime:null,
        endTime:null,
        inviteFlag:null,
        state:null,
        deleted:null,
        delTime:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(inviteActivityId) {
        this.inviteActivityService.removeById(inviteActivityId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                    this.getData();
            } else {
                this.msg.error('请求失败' + response.message);
            }
        }, error => {
            this.msg.error('请求失败' + error.message);
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
            if (searchObj.id != null) {
                pageQuery.addOnlyFilter('id', searchObj.id, 'contains');
            }
            if (searchObj.guid != null) {
                pageQuery.addOnlyFilter('guid', searchObj.guid, 'contains');
            }
            if (searchObj.title != null) {
                pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
            }
            if (searchObj.banner != null) {
                pageQuery.addOnlyFilter('banner', searchObj.banner, 'contains');
            }
            if (searchObj.rule != null) {
                pageQuery.addOnlyFilter('rule', searchObj.rule, 'contains');
            }
            if (searchObj.startTime != null) {
                pageQuery.addOnlyFilter('startTime', searchObj.startTime, 'contains');
            }
            if (searchObj.endTime != null) {
                pageQuery.addOnlyFilter('endTime', searchObj.endTime, 'contains');
            }
            if (searchObj.inviteFlag != null) {
                pageQuery.addOnlyFilter('inviteFlag', searchObj.inviteFlag, 'contains');
            }
            if (searchObj.state != null) {
                pageQuery.addOnlyFilter('state', searchObj.state, 'contains');
            }
            if (searchObj.deleted != null) {
                pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'contains');
            }
            if (searchObj.delTime != null) {
                pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'contains');
            }
        return pageQuery;
    }


}
