import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CouponService} from '../../../services/coupon.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {CouponReceiveBo} from "../../../models/domain/bo/coupon-receive-bo.model";
import {CouponReceiveService} from "../../../services/coupon-receive.service";

@Component({
    selector: 'list-stored-coupon',
    templateUrl: './list-stored-coupon.component.html',
    styleUrls: ['./list-stored-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListStoredCouponComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;
    indeterminate = false;

    couponReceive: CouponReceiveBo;

    constructor(public route: ActivatedRoute, public router: Router, private couponService: CouponService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService, public couponReceiveService: CouponReceiveService) {
        this.buildForm();
    }

    type = 1;

    ngOnInit() {
        this.pageQuery.addOnlyFilter('couponType', 3, 'eq');
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
            if (this.pageQuery.page != 1 && response['content'] == "") {
                this.pageQuery.page = this.pageQuery.page - 1;
                this.getData();
            } else {
                this.datas = response['content'];
                this.pageQuery.covertResponses(response);
                this.loading = false;
            }
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

    allChecked = false;
    allElection = false;

    /**
     * 全选
     * @param {boolean} value
     */
    checkAll(value: boolean): void {
        this.allChecked = value;
        this.datas.forEach(e => {
            e.checked = value;
        })

    }

    /**
     * 单个选择
     */
    refreshStatus(item, id): void {
        this.datas.forEach(e => {
            if (id == e.id && item.checked == true) {
                e.checked = true;

            } else if (id == e.id && item.checked == false) {
                e.checked = false;
            }
            this.allElection = true;
            this.allChecked = false;
            this.datas.forEach(e => {
                if (!e.checked) {
                    this.allElection = false;
                    return
                }
                if (e.checked) {
                    this.allChecked = true;
                }
            })
        })
    }

    couponGrant(event) {
        this.couponReceive = new CouponReceiveBo();
        let coupon: any[] = [];
        this.datas.forEach(e => {
            if (e.checked) {
                coupon.push({ id: e.id })
            }
        })
        this.couponReceive.coupons = coupon;
        this.couponReceive.members = event;
        this.couponReceiveService.grantStored(this.couponReceive).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("发放成功");
                this.allElection = false;
                this.allChecked = false;
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "发放失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }

}
