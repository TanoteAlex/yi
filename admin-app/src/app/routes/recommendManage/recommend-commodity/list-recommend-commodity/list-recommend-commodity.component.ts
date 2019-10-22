
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {PageQuery} from '../../../models/page-query.model';
import { RecommendCommodityService } from '../../../services/recommend-commodity.service';
import { RecommendCommodityListVo } from '../../../models/domain/listVo/recommend-commodity-list-vo.model';

@Component({
    selector: 'list-recommend-commodity',
    templateUrl: './list-recommend-commodity.component.html',
    styleUrls: ['./list-recommend-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRecommendCommodityComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    recommendCommoditys: Array<RecommendCommodityListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private recommendCommodityService: RecommendCommodityService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            recommendId: [null],
            commodityId: [null],
            sort: [null],
        });
    }

    sort(sort: { key: string, value: string }): void {
        this.pageQuery.addSort(sort.key, sort.value)
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
        this.recommendCommodityService.query(this.pageQuery).subscribe(response => {
            this.recommendCommoditys = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误' + error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
            recommendId: null,
            commodityId: null,
            sort: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(recommendCommodityId) {
        this.recommendCommodityService.removeById(recommendCommodityId).subscribe(response => {
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
        if (searchObj.recommendId != null) {
            pageQuery.addOnlyFilter('recommendId', searchObj.recommendId, 'contains');
        }
        if (searchObj.commodityId != null) {
            pageQuery.addOnlyFilter('commodityId', searchObj.commodityId, 'contains');
        }
        if (searchObj.sort != null) {
            pageQuery.addOnlyFilter('sort', searchObj.sort, 'contains');
        }
        return pageQuery;
    }


}
