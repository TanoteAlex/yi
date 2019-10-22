import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {PlatformSaleStatService} from '../../../services/platform-sale-stat.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {PlatformSaleStat} from "../../../models/original/platform-sale-stat.model";
import {SupplierAccountService} from "../../../services/supplier-account.service";
import {SupplierAccount} from "../../../models/original/supplier-account.model";
import {SaleOrderService} from "../../../services/sale-order.service";

@Component({
    selector: 'list-my-income',
    templateUrl: './list-my-income.component.html',
    styleUrls: ['./list-my-income.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class ListMyIncomeComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    statistics: PlatformSaleStat;

    loading = false;

    datas: any[] = [];

    saleOrders: any[] = [];

    supplierAccount: SupplierAccount = new SupplierAccount();

    dateFormat = 'yyyy/MM/dd';
    date: Date[] = [new Date, new Date]
    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, public supplierAccountService: SupplierAccountService, private platformSaleStatService: PlatformSaleStatService, public msg: NzMessageService, public saleOrderService: SaleOrderService,
        private fb: FormBuilder) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            createTime: [null]
        });
    }

    startTimes: String;
    endTimes: String;

    goplatformSaleStatController() {
        this.platformSaleStatService.search(this.startTimes, this.endTimes).subscribe(response => {
            this.statistics = response.data;
        });
    }

    changeRange(dates: Date[]) {
        if (dates != null) {
            if (dates.length != 0) {
                this.startTimes = dates[0].toLocaleDateString().replace(/\//g, '-');
                this.endTimes = dates[1].toLocaleDateString().replace(/\//g, '-');
            } else {
                this.searchForm.patchValue({
                    createTime: null
                })
                this.searchData();
            }
        }
    }

    clear() {
        this.startTimes = "";
        this.endTimes = "";
        this.goplatformSaleStatController();
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
        this.supplierAccountService.getForSupplier().subscribe(response => {
            this.supplierAccount = response.data;
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message ? error.message : "请求错误");
        });
        this.saleOrderService.queryForSupplier(this.pageQuery).subscribe(response => {
            this.saleOrders = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message ? error.message : "请求错误");
        })
    }

    clearSearch() {
        this.searchForm.reset({
            createTime: null
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(platformSaleStatId) {
        this.platformSaleStatService.removeById(platformSaleStatId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.createTime != null) {
            pageQuery.addFilter('createTime', this.startTimes, 'gte');
            pageQuery.addFilter('createTime', this.endTimes, 'lte');
        }
        pageQuery.addFilter('orderState', 4, 'eq');
        return pageQuery;
    }

}
