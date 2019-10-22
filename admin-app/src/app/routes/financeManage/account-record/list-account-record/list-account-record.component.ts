import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {AccountRecordService} from "../../../services/account-record.service";

@Component({
    selector: 'list-account-record',
    templateUrl: './list-account-record.component.html',
    styleUrls: ['./list-account-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAccountRecordComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;
    startTimes: String;
    endTimes: String;

    constructor(public route: ActivatedRoute, public router: Router, private accountRecordService: AccountRecordService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            username: [null],
            tradeType: [null],
            tradeTime: [null],
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
        this.accountRecordService.query(this.pageQuery).subscribe(response => {
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
            username: null,
            tradeType: null,
            tradeTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(accountRecordId) {
        this.accountRecordService.removeById(accountRecordId).subscribe(response => {
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
        if (searchObj.username != null) {
            pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
        }
        if (searchObj.tradeType != null) {
            pageQuery.addOnlyFilter('tradeType', searchObj.tradeType, 'eq');
        }
        if (searchObj.tradeTime != null) {
            pageQuery.addFilter('tradeTime', this.startTimes, 'gte');
            pageQuery.addFilter('tradeTime', this.endTimes, 'lte');
        }
        return pageQuery;
    }

    changeRange(dates: Date[]) {
        if (dates != null) {
            if (dates.length != 0) {
                this.startTimes = dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00";
                this.endTimes = dates[1].toLocaleDateString().replace(/\//g, '-') + " 23:59:59";
            } else {
                this.searchForm.patchValue({
                    orderTime: null
                })
                this.searchData();
            }
        }
    }

    types = [
        { id: 1, name: "佣金转入" },
        { id: 2, name: "在线支付" },
        { id: 3, name: "提现" },
        { id: 4, name: "小区提成" },
        { id: 6, name: "退货退款" },
    ]

}
