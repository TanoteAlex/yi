
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { PageQuery } from '../../../models/page-query.model';
import { OrderLogService } from '../../../services/order-log.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SUCCESS } from "../../../models/constants.model";

@Component({
    selector: 'list-order-log',
    templateUrl: './list-order-log.component.html',
    styleUrls: ['./list-order-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListOrderLogComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private orderLogService: OrderLogService, public msg: NzMessageService,
        private fb: FormBuilder,private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        orderNo:[null],
        state:[null],
        operateTime:[null],
        operator:[null],
        remark:[null],
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
        this.orderLogService.query(this.pageQuery).subscribe(response => {
            this.datas=response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误'+ error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
        orderNo:null,
        state:null,
        operateTime:null,
        operator:null,
        remark:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
            if (searchObj.orderNo != null) {
                pageQuery.addOnlyFilter('orderNo', searchObj.orderNo, 'contains');
            }
            if (searchObj.state != null) {
                pageQuery.addOnlyFilter('state', searchObj.state, 'eq');
            }
            if (searchObj.operator != null) {
                pageQuery.addOnlyFilter('operator', searchObj.operator, 'contains');
            }
        return pageQuery;
    }


}
