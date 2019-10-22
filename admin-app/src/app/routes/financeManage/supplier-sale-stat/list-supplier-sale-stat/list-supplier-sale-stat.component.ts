import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierSaleStatService} from '../../../services/supplier-sale-stat.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {SupplierSaleStat} from "../../../models/original/supplier-sale-stat.model";

@Component({
    selector: 'list-supplier-sale-stat',
    templateUrl: './list-supplier-sale-stat.component.html',
    styleUrls: ['./list-supplier-sale-stat.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class ListSupplierSaleStatComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    saleTotal: SupplierSaleStat;

    saleList: SupplierSaleStat[];

    loading = false;

    datas: any[] = [];
    dateFormat = 'yyyy/MM/dd';
    date: Date[] = [new Date, new Date]
    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private supplierSaleStatService: SupplierSaleStatService, public msg: NzMessageService,
        private fb: FormBuilder) {
        this.buildForm();
    }

    getSaleTotal() {
        this.loading = true;
        const searchObj = this.searchForm.value;
        let queryParams = {
            startTime: this.startTimes ? this.startTimes : null,
            endTime: this.endTimes ? this.endTimes : null,
            supplierName: searchObj.supplierName
        };
        this.supplierSaleStatService.getSupplierSaleTotal(queryParams).subscribe(response => {
            this.saleTotal = response.data;
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message);
        });
    }

    querySaleList() {
        this.loading = true;
        const searchObj = this.searchForm.value;
        if (searchObj.supplierName) {
            this.pageQuery.pushParams("supplierName", searchObj.supplierName);
        }
        if (this.startTimes) {
            this.pageQuery.pushParams("startTime", this.startTimes);
        }
        if (this.endTimes) {
            this.pageQuery.pushParams("endTime", this.endTimes);
        }
        this.supplierSaleStatService.querySupplierSaleList(this.pageQuery).subscribe(response => {
            this.saleList = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message);
        });
    }

    ngOnInit() {
        this.getSaleTotal();
        this.querySaleList();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            dateRange: [],
            supplierName: null,
        });
    }

    startTimes: String;
    endTimes: String;

    changeDateRange(dates: Date[]) {
        if (dates != null && dates.length != 0) {
            this.startTimes = dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00";
            this.endTimes = dates[1].toLocaleDateString().replace(/\//g, '-') + " 23:59:59";
        } else {
            this.startTimes = null;
            this.endTimes = null;
        }
        this.searchData();
    }

    sort(sort: { key: string, value: string }): void {
        this.pageQuery.addSort(sort.key, sort.value)
        this.searchData();
    }

    searchData(reset: boolean = false): void {
        if (reset) {
            this.pageQuery.resetPage();
        }
        this.getSaleTotal();
        this.querySaleList();
    }

    clearSearch() {
        this.searchForm.reset({
            dateRange: [],
            supplierName: null,
        });
        this.pageQuery.clearFilter();
        this.pageQuery.clearParams();
        this.startTimes = "";
        this.endTimes = "";
        this.searchData();
    }

}