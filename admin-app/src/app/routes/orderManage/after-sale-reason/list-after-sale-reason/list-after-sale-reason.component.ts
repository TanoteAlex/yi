import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';

import {AfterSaleReasonService} from '../../../services/after-sale-reason.service';
import {AfterSaleReasonListVo} from '../../../models/domain/listVo/after-sale-reason-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-after-sale-reason',
  templateUrl: './list-after-sale-reason.component.html',
  styleUrls: ['./list-after-sale-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListAfterSaleReasonComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  afterSaleReasons: Array<AfterSaleReasonListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private afterSaleReasonService: AfterSaleReasonService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      afterSaleType: [null],
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
    this.afterSaleReasonService.query(this.pageQuery).subscribe(response => {
      this.afterSaleReasons = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      afterSaleType: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(afterSaleReasonId) {
    this.afterSaleReasonService.removeById(afterSaleReasonId).subscribe(response => {
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
    if (searchObj.afterSaleType != null) {
      pageQuery.addOnlyFilter('afterSaleType', searchObj.afterSaleType, 'eq');
    }
    return pageQuery;
  }

  types = [
    {id: 1, name: "退款"},
    {id: 2, name: "退货退款"},
    // {id: 3, name: "换货"},
  ]

}
