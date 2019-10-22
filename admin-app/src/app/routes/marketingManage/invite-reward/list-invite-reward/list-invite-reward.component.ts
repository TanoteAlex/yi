import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SUCCESS } from '../../../models/constants.model';
import { PageQuery } from '../../../models/page-query.model';
import { InviteRewardService } from '../../../services/invite-reward.service';
import { InviteRewardListVo } from '../../../models/domain/listVo/invite-reward-list-vo.model';

@Component({
  selector: 'list-invite-reward',
  templateUrl: './list-invite-reward.component.html',
  styleUrls: ['./list-invite-reward.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ListInviteRewardComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  inviteRewards: Array<InviteRewardListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private inviteRewardService: InviteRewardService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      id: [null],
      guid: [null],
      inviteActivityId: [null],
      rewardId: [null],
      sort: [null],
      state: [null],
      deleted: [null],
      delTime: [null],
    });
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value);
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
    this.inviteRewardService.query(this.pageQuery).subscribe(response => {
      this.inviteRewards = response['content'];
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
      inviteActivityId: null,
      rewardId: null,
      sort: null,
      state: null,
      deleted: null,
      delTime: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(inviteRewardId) {
    this.inviteRewardService.removeById(inviteRewardId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success('删除成功');
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
    if (searchObj.id != null) {
      pageQuery.addOnlyFilter('id', searchObj.id, 'contains');
    }
    if (searchObj.guid != null) {
      pageQuery.addOnlyFilter('guid', searchObj.guid, 'contains');
    }
    if (searchObj.inviteActivityId != null) {
      pageQuery.addOnlyFilter('inviteActivityId', searchObj.inviteActivityId, 'contains');
    }
    if (searchObj.rewardId != null) {
      pageQuery.addOnlyFilter('rewardId', searchObj.rewardId, 'contains');
    }
    if (searchObj.sort != null) {
      pageQuery.addOnlyFilter('sort', searchObj.sort, 'contains');
    }
    if (searchObj.state != null) {
      pageQuery.addOnlyFilter('state', searchObj.state, 'contains');
    }
    if (searchObj.deleted != null) {
      pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'contains');
    }
    if (searchObj.delTime != null) {
      pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'contains');
    }
    return pageQuery;
  }


}
