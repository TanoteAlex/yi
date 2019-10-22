import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CommunityService} from '../../../services/community.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-community',
    templateUrl: './list-community.component.html',
    styleUrls: ['./list-community.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListCommunityComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private communityService: CommunityService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    chooseSwitch(i) {
        let state = this.datas[i].state, id = this.datas[i].id;
        if (state == 0) {
            //禁用
            this.communityService.updateState(id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msg.success("禁用成功");
                    this.datas[i].state = 1
                } else {
                    this.msg.error(response.message ? response.message : "请求失败");
                }
            }, error => {
                this.msg.error(error.message ? error.message : "请求错误");
            });
        } else if (state == 1) {
            //启用
            this.communityService.updateState(id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msg.success("启用成功");
                    this.datas[i].state = 0
                } else {
                    this.msg.error(response.message ? response.message : "请求失败");
                }
            }, error => {
                this.msg.error(error.message ? error.message : "请求错误");
            });
        }
    }

    buildForm() {
        this.searchForm = this.fb.group({
            province: [null],
            address: [null],
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
        this.communityService.query(this.pageQuery).subscribe(response => {
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
            province: null,
            address: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(communityId) {
        this.communityService.removeById(communityId).subscribe(response => {
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
        if (searchObj.province != null) {
            pageQuery.addOnlyFilter('province', searchObj.province, 'contains');
        }
        if (searchObj.address != null) {
            pageQuery.addOnlyFilter('address', searchObj.address, 'contains');
        }
        return pageQuery;
    }

    privinces = [
        { name: "北京市", code: "110000" },
        { name: "天津市", code: "120000" },
        { name: "河北省", code: "130000" },
        { name: "山西省", code: "140000" },
        { name: "内蒙古自治区", code: "150000" },
        { name: "辽宁省", code: "210000" },
        { name: "吉林省", code: "220000" },
        { name: "黑龙江省", code: "230000" },
        { name: "上海市", code: "310000" },
        { name: "江苏省", code: "320000" },
        { name: "浙江省", code: "330000" },
        { name: "安徽省", code: "340000" },
        { name: "福建省", code: "350000" },
        { name: "江西省", code: "360000" },
        { name: "山东省", code: "370000" },
        { name: "河南省", code: "370000" },
        { name: "湖北省", code: "420000" },
        { name: "湖南省", code: "430000" },
        { name: "广东省", code: "440000" },
        { name: "广西壮族自治区", code: "450000" },
        { name: "海南省", code: "460000" },
        { name: "重庆市", code: "500000" },
        { name: "四川省", code: "510000" },
        { name: "贵州省", code: "520000" },
        { name: "云南省", code: "530000" },
        { name: "西藏自治区", code: "540000" },
        { name: "陕西省", code: "610000" },
        { name: "甘肃省", code: "620000" },
        { name: "青海省", code: "630000" },
        { name: "宁夏回族自治区", code: "640000" },
        { name: "新疆维吾尔自治区", code: "650000" },
        { name: "台湾省", code: "710000" },
        { name: "香港特别行政区", code: "810000" },
        { name: "澳门特别行政区", code: "820000" },
    ];

}
