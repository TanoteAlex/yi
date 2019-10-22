import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InvitePrizeService } from '../../../services/invite-prize.service';
import { InvitePrizeListVo } from '../../../models/domain/listVo/invite-prize-list-vo.model';
import { PageQuery } from '../../../models/page-query.model';
import { SUCCESS } from '../../../models/constants.model';

@Component({
  selector: 'list-invite-prize',
  templateUrl: './list-invite-prize.component.html',
  styleUrls: ['./list-invite-prize.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ListInvitePrizeComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  invitePrizes: Array<InvitePrizeListVo>;

  expandForm = false;

  prizeTypes = [{
    code: 1,
    title: '积分',
  }, {
    code: 2,
    title: '商品',
  }, {
    code: 3,
    title: '优惠券',
  }];

  constructor(public route: ActivatedRoute, public router: Router, private invitePrizeService: InvitePrizeService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      prizeNo: [null],
      prizeName: [null],
      prizeType: [null],
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
    this.invitePrizeService.query(this.pageQuery).subscribe(response => {
      this.invitePrizes = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      prizeNo: null,
      prizeName: null,
      prizeType: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(invitePrizeId) {
    this.invitePrizeService.removeById(invitePrizeId).subscribe(response => {
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

    if (searchObj.prizeNo != null) {
      pageQuery.addOnlyFilter('prizeNo', searchObj.prizeNo, 'contains');
    }
    if (searchObj.prizeName != null) {
      pageQuery.addOnlyFilter('prizeName', searchObj.prizeName, 'contains');
    }
    if (searchObj.prizeType != null) {
      pageQuery.addOnlyFilter('prizeType', searchObj.prizeType, 'eq');
    }
    return pageQuery;
  }


}
