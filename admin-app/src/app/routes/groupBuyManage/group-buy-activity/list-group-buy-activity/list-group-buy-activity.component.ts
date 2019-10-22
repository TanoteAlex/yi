import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GroupBuyActivityService} from '../../../services/group-buy-activity.service';
import {GroupBuyActivityListVo} from '../../../models/domain/listVo/group-buy-activity-list-vo.model';
import {SUCCESS} from "../../../models/constants.model";
import {PageQuery} from "../../../models/page-query.model";

@Component({
    selector: 'list-group-buy-activity',
    templateUrl: './list-group-buy-activity.component.html',
    styleUrls: ['./list-group-buy-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListGroupBuyActivityComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    groupBuyActivitys: Array<GroupBuyActivityListVo>;

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private groupBuyActivityService: GroupBuyActivityService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    auditeds = [
        { id: 0, name: "未审核" },
        { id: 1, name: "已审核" }
    ]

    publisheds = [
        { id: 0, name: "未发布" },
        { id: 1, name: "已发布" }
    ]


    buildForm() {
        this.searchForm = this.fb.group({
            activityName: [null],
            audited: [null],
            published: [null],
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
        this.groupBuyActivityService.query(this.pageQuery).subscribe(response => {
            this.groupBuyActivitys = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误' + error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
            activityName: null,
            audited: null,
            published: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(groupBuyActivityId) {
        this.groupBuyActivityService.removeById(groupBuyActivityId).subscribe(response => {
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
        if (searchObj.activityName != null) {
            pageQuery.addOnlyFilter('activityName', searchObj.activityName, 'contains');
        }
        if (searchObj.audited != null) {
            pageQuery.addOnlyFilter('audited', searchObj.audited, 'contains');
        }
        if (searchObj.published != null) {
            pageQuery.addOnlyFilter('published', searchObj.published, 'contains');
        }
        return pageQuery;
    }

    menus = [
        { name: "所有活动", value: "" },
        { name: "已审核", value: 1 },
        { name: "待审核", value: 0 },
    ];

    onItemClick(i) {
        //        this.pageQuery.clearFilter();
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyFilter('audited', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('audited');
        this.getData();
    }

    auditing(groupBuyActivityId, event) {
        this.groupBuyActivityService.auditing(groupBuyActivityId, event.id).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success('审核通过')
                this.getData();
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

  publish(groupBuyActivityId) {
    this.groupBuyActivityService.publish(groupBuyActivityId).subscribe(response => {
      if (response.result == SUCCESS) {
        if(response.data){
          this.msg.success("发布成功");
          this.getData();
        }else{
          this.msg.warning("该活动不在有效活动时间段，请核对！")
        }
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }

}
