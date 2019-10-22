import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CouponService} from '../../../services/coupon.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-coupon',
    templateUrl: './list-coupon.component.html',
    styleUrls: ['./list-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListCouponComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private couponService: CouponService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    type = 1;

    menus = [
        { name: "满减券", value: 1 },
        { name: "买送券", value: 2 }
    ];

    onItemClick(i) {
        //    this.pageQuery.clearFilter();
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyFilter('couponType', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('couponType');
        this.getData();
    }


    ngOnInit() {

        this.pageQuery.addOnlyFilter('couponType', this.type, 'eq');
        /*this.searchData();*/
        this.getData();
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
        this.couponService.query(this.pageQuery).subscribe(response => {
            this.datas = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误' + error.message);
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
                this.msg.error('请求失败' + response.message);
            }
        }, error => {
            this.msg.error('请求失败' + error.message);
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
