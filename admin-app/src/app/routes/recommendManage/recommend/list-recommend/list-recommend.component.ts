import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {RecommendService} from '../../../services/recommend.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {PositionService} from "../../../services/position.service";

@Component({
    selector: 'list-recommend',
    templateUrl: './list-recommend.component.html',
    styleUrls: ['./list-recommend.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRecommendComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    dataPosition: any[] = [];

    expandForm = false;

    position: PageQuery = new PageQuery();

    positionTypes = []

    constructor(public route: ActivatedRoute, public router: Router, private recommendService: RecommendService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService, public positionService: PositionService) {
        this.buildForm();
    }

    ngOnInit() {
        this.position.addOnlyFilter("positionType", "1", "neq");
        this.positionService.query(this.position).subscribe(response => {
            this.dataPosition = response['content'];
            this.dataPosition.forEach(e => {
                this.positionTypes.push({
                    code: e.positionType,
                    title: e.name,
                })
            })
        })
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            title: [null],
            positionType: [null]
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
        this.recommendService.query(this.pageQuery).subscribe(response => {
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
            title: null,
            positionType: null
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(recommendId) {
        this.recommendService.removeById(recommendId).subscribe(response => {
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
        if (searchObj.title != null) {
            pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
        }
        if (searchObj.positionType != null) {
            pageQuery.addOnlyFilter('position.positionType', searchObj.positionType, 'eq');
        }
        return pageQuery;
    }

    chooseSwitch(i) {
        let state = this.datas[i].state, id = this.datas[i].id;
        if (state == 0) {
            //禁用
            this.recommendService.updateStateDisable(id).subscribe(response => {
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
            this.recommendService.updateStateEnable(id).subscribe(response => {
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

}
