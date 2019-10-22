import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GroupBuyOrderService} from '../../../services/group-buy-order.service';
import {GroupBuyOrderListVo} from '../../../models/domain/listVo/group-buy-order-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-group-buy-order',
    templateUrl: './list-group-buy-order.component.html',
    styleUrls: ['./list-group-buy-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListGroupBuyOrderComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    groupBuyOrders: Array<GroupBuyOrderListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private groupBuyOrderService: GroupBuyOrderService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.pageQuery.addOnlyFilter('groupType', 1, 'eq');
        this.pageQuery.addLockFilterName('groupType');
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            member: [null],
            groupBuyActivityProduct: [null],
            saleOrder: [null],
            state: [null],
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
        this.groupBuyOrderService.query(this.pageQuery).subscribe(response => {
            this.groupBuyOrders = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误' + error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
            member: null,
            groupBuyActivityProduct: null,
            saleOrder: null,
            state: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(groupBuyOrderId) {
        this.groupBuyOrderService.removeById(groupBuyOrderId).subscribe(response => {
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
        if (searchObj.member != null) {
            pageQuery.addOnlyFilter('member', searchObj.member, 'contains');
        }
        if (searchObj.groupBuyActivityProduct != null) {
            pageQuery.addOnlyFilter('groupBuyActivityProduct', searchObj.groupBuyActivityProduct, 'contains');
        }
        if (searchObj.saleOrder != null) {
            pageQuery.addOnlyFilter('saleOrder', searchObj.saleOrder, 'contains');
        }
        if (searchObj.state != null) {
            pageQuery.addOnlyFilter('state', searchObj.state, 'eq');
        }
        return pageQuery;
    }

    states = [
        { id: 1, name: "待付款" },
        { id: 2, name: "拼团中" },
        { id: 3, name: "已成团" },
        { id: 4, name: "已失效" },
    ]

}
