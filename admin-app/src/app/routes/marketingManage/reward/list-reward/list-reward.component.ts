import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {RewardService} from '../../../services/reward.service';
import {RewardListVo} from '../../../models/domain/listVo/reward-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";

@Component({
    selector: 'list-reward',
    templateUrl: './list-reward.component.html',
    styleUrls: ['./list-reward.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRewardComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    rewards: Array<RewardListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private rewardService: RewardService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            name: [null],
            rewardType: [null],
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
        this.rewardService.query(this.pageQuery).subscribe(response => {
            this.rewards = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message ? error.message : "请求错误");
        });
    }

    clearSearch() {
        this.searchForm.reset({
            name: null,
            rewardType: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(rewardId) {
        this.rewardService.removeById(rewardId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求错误");
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.name != null) {
            pageQuery.addOnlyFilter('name', searchObj.name, 'contains');
        }
        if (searchObj.rewardType != null) {
            pageQuery.addOnlyFilter('rewardType', searchObj.rewardType, 'eq');
        }
        return pageQuery;
    }

    rewardTypes = [
        { id: 1, name: "邀请" },
        { id: 2, name: "评论" },
        { id: 3, name: "连续签到" },
    ]

    chooseSwitch(i) {
        let state = this.rewards[i].state, id = this.rewards[i].id;
        if (state == 0) {
            //禁用
            this.rewardService.updateState(id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msg.success("禁用成功");
                    this.rewards[i].state = 1
                } else {
                    this.msg.error(response.message ? response.message : "请求失败");
                }
            }, error => {
                this.msg.error(error.message ? error.message : "请求错误");
            });
        } else if (state == 1) {
            //启用
            this.rewardService.updateState(id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msg.success("启用成功");
                    this.rewards[i].state = 0
                } else {
                    this.msg.error(response.message ? response.message : "请求失败");
                }
            }, error => {
                this.msg.error(error.message ? error.message : "请求错误");
            });
        }
    }

}
