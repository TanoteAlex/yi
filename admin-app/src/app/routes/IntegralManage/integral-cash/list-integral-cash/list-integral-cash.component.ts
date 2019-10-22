
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { IntegralCashService } from '../../../services/integral-cash.service';
import { IntegralCashListVo } from '../../../models/domain/listVo/integral-cash-list-vo.model';
import { PageQuery } from '../../../models/page-query.model';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'list-integral-cash',
    templateUrl: './list-integral-cash.component.html',
    styleUrls: ['./list-integral-cash.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListIntegralCashComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    integralCashs: Array<IntegralCashListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private integralCashService: IntegralCashService, public msg: NzMessageService,
        private fb: FormBuilder,private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        integral:[null],
        cash:[null],
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
        this.integralCashService.query(this.pageQuery).subscribe(response => {
            this.integralCashs=response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误'+ error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
        integral:null,
        cash:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(integralCashId) {
        this.integralCashService.removeById(integralCashId).subscribe(response => {
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
            if (searchObj.integral != null) {
                pageQuery.addOnlyFilter('integral', searchObj.integral, 'contains');
            }
            if (searchObj.cash != null) {
                pageQuery.addOnlyFilter('cash', searchObj.cash, 'contains');
            }
        return pageQuery;
    }


}
