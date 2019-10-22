import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {PlatformSaleStatService} from '../../../services/platform-sale-stat.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {PlatformSaleStat} from "../../../models/original/platform-sale-stat.model";

@Component({
    selector: 'list-platform-sale-stat',
    templateUrl: './list-platform-sale-stat.component.html',
    styleUrls: ['./list-platform-sale-stat.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListPlatformSaleStatComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    statistics: PlatformSaleStat;

    loading = false;

    datas: any[] = [];
    dateFormat = 'yyyy/MM/dd';
    date: Date[] = [new Date, new Date]
    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private platformSaleStatService: PlatformSaleStatService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            range: []
            /*  id:[null],
              guid:[null],
              platformOrderNum:[null],
              saleAmount:[null],
              platformRatio:[null],
              cost:[null],
              profit:[null],
              statTime:[null],*/
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
        this.startTimes = dates[0].toLocaleDateString().replace(/\//g, '-');
        this.endTimes = dates[1].toLocaleDateString().replace(/\//g, '-');
    }

    clear() {
        this.startTimes = "",
            this.endTimes = ""
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
        this.getData();
    }

    getData() {
        this.loading = true;
        this.platformSaleStatService.query(this.pageQuery).subscribe(response => {
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
            id: null,
            guid: null,
            platformOrderNum: null,
            saleAmount: null,
            platformRatio: null,
            cost: null,
            profit: null,
            statTime: null,
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
                this.msg.error('请求失败' + response.message);
            }
        }, error => {
            this.msg.error('请求失败' + error.message);
        });
    }

}
