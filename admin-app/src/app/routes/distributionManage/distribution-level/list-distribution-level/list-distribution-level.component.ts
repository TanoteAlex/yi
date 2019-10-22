
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DistributionLevelService } from '../../../services/distribution-level.service';
import { DistributionLevelListVo } from '../../../models/domain/listVo/distribution-level-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-distribution-level',
    templateUrl: './list-distribution-level.component.html',
    styleUrls: ['./list-distribution-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListDistributionLevelComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    distributionLevels: Array<DistributionLevelListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private distributionLevelService: DistributionLevelService, public msg: NzMessageService,
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
        name:[null],
        rank:[null],
        commissionRate:[null],
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
        this.distributionLevelService.query(this.pageQuery).subscribe(response => {
            this.distributionLevels=response['content'];
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
        name:null,
        rank:null,
        commissionRate:null,
        remark:null,
        createTime:null,
        deleted:null,
        delTime:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(distributionLevelId) {
        this.distributionLevelService.removeById(distributionLevelId).subscribe(response => {
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
            if (searchObj.name != null) {
                pageQuery.addOnlyFilter('name', searchObj.name, 'contains');
            }
            if (searchObj.rank != null) {
                pageQuery.addOnlyFilter('rank', searchObj.rank, 'contains');
            }
            if (searchObj.commissionRate != null) {
                pageQuery.addOnlyFilter('commissionRate', searchObj.commissionRate, 'contains');
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
