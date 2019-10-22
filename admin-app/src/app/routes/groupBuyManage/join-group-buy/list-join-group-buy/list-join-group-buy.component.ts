
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { JoinGroupBuyService } from '../../../services/join-group-buy.service';
import { JoinGroupBuyListVo } from '../../../models/domain/listVo/join-group-buy-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-join-group-buy',
    templateUrl: './list-join-group-buy.component.html',
    styleUrls: ['./list-join-group-buy.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListJoinGroupBuyComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    joinGroupBuys: Array<JoinGroupBuyListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private joinGroupBuyService: JoinGroupBuyService, public msg: NzMessageService,
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
        member:[null],
        groupBuyOrder:[null],
        groupBuyActivityProduct:[null],
        saleOrder:[null],
        state:[null],
        startTime:[null],
        endTime:[null],
        remark:[null],
        createTime:[null],
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
        this.joinGroupBuyService.query(this.pageQuery).subscribe(response => {
            this.joinGroupBuys=response['content'];
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
        member:null,
        groupBuyOrder:null,
        groupBuyActivityProduct:null,
        saleOrder:null,
        state:null,
        startTime:null,
        endTime:null,
        remark:null,
        createTime:null,
        deleted:null,
        delTime:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(joinGroupBuyId) {
        this.joinGroupBuyService.removeById(joinGroupBuyId).subscribe(response => {
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
            if (searchObj.member != null) {
                pageQuery.addOnlyFilter('member', searchObj.member, 'contains');
            }
            if (searchObj.groupBuyOrder != null) {
                pageQuery.addOnlyFilter('groupBuyOrder', searchObj.groupBuyOrder, 'contains');
            }
            if (searchObj.groupBuyActivityProduct != null) {
                pageQuery.addOnlyFilter('groupBuyActivityProduct', searchObj.groupBuyActivityProduct, 'contains');
            }
            if (searchObj.saleOrder != null) {
                pageQuery.addOnlyFilter('saleOrder', searchObj.saleOrder, 'contains');
            }
            if (searchObj.state != null) {
                pageQuery.addOnlyFilter('state', searchObj.state, 'contains');
            }
            if (searchObj.startTime != null) {
                pageQuery.addOnlyFilter('startTime', searchObj.startTime, 'contains');
            }
            if (searchObj.endTime != null) {
                pageQuery.addOnlyFilter('endTime', searchObj.endTime, 'contains');
            }
            if (searchObj.remark != null) {
                pageQuery.addOnlyFilter('remark', searchObj.remark, 'contains');
            }
            if (searchObj.createTime != null) {
                pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'contains');
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
