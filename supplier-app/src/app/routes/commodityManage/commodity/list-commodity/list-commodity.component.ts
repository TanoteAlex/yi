import {Component, OnInit} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CommodityService} from '../../../services/commodity.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-commodity',
    templateUrl: './list-commodity.component.html',
})
export class ListCommodityComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    datas: any[] = [];

    loading = false;

    expandForm = false;

    listCommodityId = [];
    select = [];
    button = false;

    constructor(public route: ActivatedRoute, private router: Router, private commodityService: CommodityService, public msg: NzMessageService, private fb: FormBuilder, public modalService: NzModalService) {
        this.buildForm();
    }

    menus = [
        { name: "全部商品", value: "" },
        { name: "已上架", value: 1 },
        { name: "仓库中", value: 2 },
    ];

    onItemClick(i) {
//        this.pageQuery.clearFilter();
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyFilter('shelfState', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('shelfState');
        this.getData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            commodityName: [null],
        });
    }

    ngOnInit() {
        this.pageQuery.addOnlyFilter('auditState', 2, 'eq');
        this.pageQuery.addLockFilterName('auditState');
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
        this.configPageQuery(this.pageQuery);
        this.getData();
    }

    getData() {
        this.loading = true;
        this.commodityService.queryForSupplier(this.pageQuery).subscribe(response => {
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
            commodityName: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(commodityId) {
        this.commodityService.removeByIdForSupplier(commodityId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                // this.pageQuery.requests.page = 1;
                this.getData();
            } else {
                this.msg.error('请求失败' + response.message);
            }
        }, error => {
            this.msg.error('请求失败' + error.message);
        });
    }

    upShelf(commodityId) {
        this.commodityService.upOrDownShelfForSupplier(commodityId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("上架成功");
                /*this.router.navigate(['/pages/member/list']);*/
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }


    downShelf(commodityId) {
        this.commodityService.upOrDownShelfForSupplier(commodityId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("下架成功");
                /*this.router.navigate(['/pages/member/list']);*/
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
        if (searchObj.commodityName != null) {
            //      pageQuery.addOnlyFilter('commodityName', searchObj.commodityName, 'contains');
            pageQuery.pushParams("name", searchObj.commodityName);
        }
        return pageQuery;
    }

    /**
     * 全选
     * @param {boolean} value
     */
    checkAll(value: boolean): void {
        this.button = value;
        this.datas.forEach(e => {
            e.checked = value;
        })

    }

    allElection = false;

    /**
     * 单个选择
     */
    refreshStatus(item, id): void {
        this.datas.forEach(e => {
            if (id == e.id && item.checked == true) {
                e.c = true;

            } else if (id == e.id && item.checked == false) {
                e.checked = false;
            }
            this.allElection = true;
            this.button = false;
            this.datas.forEach(e => {
                if (!e.checked) {
                    this.allElection = false;
                    return
                }
                if (e.checked) {
                    this.button = true;
                }
            })
        })
    }

    /**
     * 批量上架
     */
    batchUpShelf() {
        this.listCommodityId = [];
        this.datas.forEach(e => {
            if (e.checked && e.shelfState == 2) {
                this.listCommodityId.push(e.id)
            }
        })
        this.commodityService.batchUpShelf(this.listCommodityId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("批量上架成功");
                this.allElection = false;
                this.button = false;
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }

    batchDownShelf() {
        this.listCommodityId = [];
        this.datas.forEach(e => {
            if (e.checked && e.shelfState == 1) {
                this.listCommodityId.push(e.id)
            }
        })
        this.commodityService.batchDownShelf(this.listCommodityId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("批量下架成功");
                this.allElection = false;
                this.button = false;
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }

}
