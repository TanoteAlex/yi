import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CouponService} from '../../../services/coupon.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-voucher',
    templateUrl: './list-voucher.component.html',
    styleUrls: ['./list-voucher.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListVoucherComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private couponService: CouponService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }


    ngOnInit() {
        this.pageQuery.addOnlyFilter('couponType', 2, 'eq');
        this.searchData();
    }


    buildForm() {
        this.searchForm = this.fb.group({
            couponName: [null],
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
        this.pageQuery.addOnlyFilter('couponType', 2, 'eq');
        this.couponService.query(this.pageQuery).subscribe(response => {
            this.datas = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message ? error.message : "请求错误");
        });
    }

    clearSearch() {
        this.searchForm.reset({
            couponName: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(couponId) {
        this.couponService.removeById(couponId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求错误");
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.couponName != null) {
            pageQuery.addOnlyFilter('couponName', searchObj.couponName, 'contains');
        }
        return pageQuery;
    }


}
