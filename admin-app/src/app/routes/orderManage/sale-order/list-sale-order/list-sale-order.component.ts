import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SaleOrderService} from '../../../services/sale-order.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {ExpressService} from "../../../services/express.service";
import {SaleOrderListVo} from "../../../models/domain/listVo/sale-order-list-vo.model";
import {environment} from "@env/environment";

@Component({
    selector: 'list-sale-order',
    templateUrl: './list-sale-order.component.html',
    styleUrls: ['./list-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSaleOrderComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    dateFormat = 'yyyy/MM/dd';
    startTimes: String;
    endTimes: String;

    totalOrderNum: number = 0;
    waitPayNum: number = 0;
    waitDeliveryNum: number = 0;
    waitReceiptNum: number = 0;
    alreadyFinishNum: number = 0;
    alreadyCloseNum: number = 0;
    afterSaleNum: number = 0;

    constructor(public route: ActivatedRoute, public router: Router, private saleOrderService: SaleOrderService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService, private expressService: ExpressService) {//Express
        this.buildForm();
    }

    menus = [];

    orderTypes = [
        { name: "普通订单", id: 0 },
//        { name: "礼物订单", id: 1 },
        { name: "团购订单", id: 2 },
        { name: "奖品订单", id: 3 },
    ]

    onItemClick(i) {
//        this.pageQuery.clearFilter();
//        this.pageQuery.clearParams();
        //重置分页
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyOneFilter('orderState', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('orderState');
        this.getData();
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            if (params["objId"] != null && params["objId"] == 2) {
                this.onItemClick(params["objId"]);
            } else if (params["objId"] != null && params["objId"] == 1) {
                this.routeDate();
            } else if (params["objId"] != null && params["objId"] == 3) {
                // this.pageQuery.addOnlyOneFilter('orderState', 4, 'eq');
                this.routeDate();
            }
        });
        this.getOrderNum();
        this.searchData();
    }

    routeDate() {
        let date = new Date();
        this.startTimes = date.toLocaleDateString().replace(/\//g, '-') + " 00:00:00";
        this.endTimes = date.toLocaleDateString().replace(/\//g, '-') + " 23:59:59";
        this.searchForm.value.createTime = this.startTimes;
        this.pageQuery.addOnlyOneFilter('createTime', this.startTimes, 'gte');
        this.pageQuery.addOnlyOneFilter('createTime', this.endTimes, 'lte');
    }

    buildForm() {
        this.searchForm = this.fb.group({
            orderNo: [null],
            username: [null],
            commodityName: [null],
            supplierName: [null],
            createTime: [null],
            consignee: [null],
            consigneePhone: [null],
            orderType: [null],
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

    downloadExcel(reset: boolean = false): void {
        if (reset) {
            this.pageQuery.resetPage();
        }
        this.configPageQuery(this.pageQuery);
        this.saleOrderService.download("GET", "销售订单-" + new Date().getTime() + ".xls", "/saleOrder/exportExcel", this.pageQuery);
    }

    getOrderNum() {
        //查询各状态订单数量
        this.saleOrderService.getOrderNum().subscribe(response => {
            this.waitPayNum = response.data.waitPayNum;
            this.waitDeliveryNum = response.data.waitDeliveryNum;
            this.waitReceiptNum = response.data.waitReceiptNum;
            this.alreadyFinishNum = response.data.alreadyFinishNum;
            this.alreadyCloseNum = response.data.alreadyCloseNum;
            this.afterSaleNum = response.data.afterSaleNum;
            this.totalOrderNum = response.data.totalOrderNum;

            this.menus = [
                { name: "全部订单（" + this.totalOrderNum + "）", value: "" },
                { name: "待付款（" + this.waitPayNum + "）", value: 1 },
                { name: "待发货（" + this.waitDeliveryNum + "）", value: 2 },
                { name: "已发货（" + this.waitReceiptNum + "）", value: 3 },
                { name: "已完成（" + this.alreadyFinishNum + "）", value: 4 },
                { name: "已关闭（" + this.alreadyCloseNum + "）", value: 5 },
            ];
        }, error => {
            this.menus = [
                { name: "全部订单", value: "" },
                { name: "待付款", value: 1 },
                { name: "待发货", value: 2 },
                { name: "已发货", value: 3 },
                { name: "已完成", value: 4 },
                { name: "已关闭", value: 5 },
            ];
        });
    }

    getData() {
        this.loading = true;
        this.saleOrderService.query(this.pageQuery).subscribe(response => {
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
            orderNo: null,
            username: null,
            commodityName: null,
            supplierName: null,
            createTime: null,
            consignee: null,
            consigneePhone: null,
            orderType: null,
        });
        this.pageQuery.clearFilter();
        this.pageQuery.clearParams();
        this.searchData();
    }

    remove(saleOrderId) {
        this.saleOrderService.removeById(saleOrderId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : '请求失败');
            }
        }, error => {
            this.msg.error(error.message ? error.message : '请求错误');
        });
    }

    goUpdateOrderState(id, orderState) {
        this.saleOrderService.updateOrderState(id, orderState).subscribe(response => {
            if (response.result == SUCCESS) {
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : '请求失败');
            }
        }, error => {
            this.msg.error(error.message ? error.message : '请求错误');
        })
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        pageQuery.clearParams();
        const searchObj = this.searchForm.value;
        if (searchObj.orderNo != null) {
            pageQuery.addOnlyFilter('orderNo', searchObj.orderNo, 'contains');
        }
        if (searchObj.username != null) {
            pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
        }
        if (searchObj.supplierName != null) {
            pageQuery.addOnlyFilter('supplier.supplierName', searchObj.supplierName, 'contains');
        }
        if (searchObj.commodityName != null) {
            pageQuery.pushParams("commodityName", searchObj.commodityName);
        }
        if (searchObj.createTime != null) {
            pageQuery.addFilter('createTime', this.startTimes, 'gte');
            pageQuery.addFilter('createTime', this.endTimes, 'lte');
        }
        if (searchObj.consignee != null) {
            pageQuery.addOnlyFilter('consignee', searchObj.consignee, 'contains');
        }
        if (searchObj.consigneePhone != null) {
            pageQuery.addOnlyFilter('consigneePhone', searchObj.consigneePhone, 'contains');
        }
        if (searchObj.orderType != null) {
            pageQuery.addOnlyFilter('orderType', searchObj.orderType, 'eq');
        }
        return pageQuery;
    }

    deliver() {
        this.getData();
    }

    changeRange(dates: Date[]) {
        if (dates != null) {
            if (dates.length != 0) {
                this.startTimes = dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00";
                this.endTimes = dates[1].toLocaleDateString().replace(/\//g, '-') + " 23:59:59";
            } else {
                this.searchForm.patchValue({
                    createTime: null
                })
                this.searchData();
            }
        }
    }

}
