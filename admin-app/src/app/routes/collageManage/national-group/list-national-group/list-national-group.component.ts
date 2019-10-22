import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {NationalGroupService} from '../../../services/national-group.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-national-group',
    templateUrl: './list-national-group.component.html',
    styleUrls: ['./list-national-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListNationalGroupComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    dateFormat = 'yyyy/MM/dd';

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private nationalGroupService: NationalGroupService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    menus = [
        { name: "所有拼团", value: "所有拼团" },
        { name: "进行中", value: "进行中" },
        { name: "未开始", value: "未开始" },
        { name: "已结束", value: "已结束" },
    ];

    time: Date;

    onItemClick(i) {
//        this.pageQuery.clearFilter();
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.time = new Date()
        if (this.menus[i].value == "进行中") {
            this.pageQuery.addOnlyFilter('startTime', this.time, 'ge');
            this.pageQuery.addOnlyFilter('endTime', this.time, 'lt');
            this.pageQuery.addLockFilterName('startTime');
            this.pageQuery.addLockFilterName('endTime');
        }
        if (this.menus[i].value == "未开始") {
            this.pageQuery.addOnlyFilter('startTime', this.time, 'lt');
            this.pageQuery.addLockFilterName('startTime');
        }
        if (this.menus[i].value == "已结束") {
            this.pageQuery.addOnlyFilter('endTime', this.time, 'ge');
            this.pageQuery.addLockFilterName('endTime');
        }
        this.getData();
    }

    startTimes: String;

    endTimes: String;

    allChecked = false;
    indeterminate = false;
    select = [];

    checkAll(value: boolean): void {
        this.datas.forEach(e => {
            e.checked = value;
        })
        if (value) {
            this.select = this.datas
        } else {
            this.select = []
        }
    }

    refreshStatus(item, id): void {
        this.datas.forEach(e => {
            if (id == e.id && item.checked == true) {
                e.checked = true;
            } else if (id == e.id && item.checked == false) {
                e.checked = false;
            }
        })
    }

    changeRange(dates: Date[]) {
        this.startTimes = dates[0].toLocaleDateString().replace(/\//g, '-');
        this.endTimes = dates[1].toLocaleDateString().replace(/\//g, '-');

    }

    buildForm() {
        this.searchForm = this.fb.group({
            productName: [null],
            range: []
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
        this.nationalGroupService.query(this.pageQuery).subscribe(response => {
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
            productName: null,
            range: []
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(nationalGroupId) {
        this.nationalGroupService.removeById(nationalGroupId).subscribe(response => {
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
        if (searchObj.productId != null) {
            pageQuery.addOnlyFilter('product.productName', searchObj.productName, 'contains');
        }
        return pageQuery;
    }


}
