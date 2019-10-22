import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {CommodityService} from "../../../services/commodity.service";
import {Commodity} from "../../../models/original/commodity.model";
import {Location} from "@angular/common";
import {FormGroup} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
    selector: 'view-commodity',
    templateUrl: './view-commodity.component.html',
    styleUrls: ['./view-commodity.component.less'],
})
export class ViewCommodityComponent implements OnInit {

    commodity: Commodity = new Commodity;

    commonForm: FormGroup;

    constructor(
        private route: ActivatedRoute,
        public msgSrv: NzMessageService,
        public http: _HttpClient,
        public msg: NzMessageService,
        private commodityService: CommodityService,
        private location: Location,
        public sanli: DomSanitizer
    ) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });

    }

    total = 0;       //总记录数
    pageSize: number = 5;  //页的大小，初始值为5
    page: number = 1;       //页号，初始为1
    totalPages = 0; //总页数

    //上一页
    previousPage() {
        this.page--;
        this.changePage(this.page);
    }

    // 下一页
    nextPage() {
        this.page++;
        this.changePage(this.page);
    }


    // 首页
    topPage() {
        this.page = 1;
    }

    // 尾页
    endPage() {
        this.page = this.totalPages;
    }

    // 改变页号，更新表格所在页数的数据
    changePage(page: number): void {
        if (page > 0 && page < this.totalPages) { //正常之间的
            this.page = page;
            //以防改变页的大小或总记录数/页大小时不为整，出现小数点形式的
            this.totalPages = Math.ceil(this.total / this.pageSize);
        } else if (page <= 0) { //页号小于首页
            this.page = 1;
        } else { //页号大于尾页号
            this.page = this.totalPages;
        }
    }

    getById(objId) {
        this.commodityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.commodity = response.data;
                this.total = this.commodity.products.length;
                this.totalPages = Math.ceil(this.total / this.pageSize);
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
