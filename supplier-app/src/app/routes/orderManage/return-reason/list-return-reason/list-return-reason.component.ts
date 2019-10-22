import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {ReturnReasonService} from '../../../services/return-reason.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-return-reason',
  templateUrl: './list-return-reason.component.html',
  styleUrls: ['./list-return-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListReturnReasonComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private returnReasonService: ReturnReasonService, public msg: NzMessageService,
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
      reasonType: [null],
      sort: [null],
      state: [null],
      createTime: [null],
      deleted: [null],
      delTime: [null],
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
    this.returnReasonService.query(this.pageQuery).subscribe(response => {
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
      reasonType: null,
      sort: null,
      state: null,
      createTime: null,
      deleted: null,
      delTime: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(returnReasonId) {
    this.returnReasonService.removeById(returnReasonId).subscribe(response => {
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
    if (searchObj.id != null) {
      pageQuery.addOnlyFilter('id', searchObj.id, 'contains');
    }
    if (searchObj.guid != null) {
      pageQuery.addOnlyFilter('guid', searchObj.guid, 'contains');
    }
    if (searchObj.reasonType != null) {
      pageQuery.addOnlyFilter('reasonType', searchObj.reasonType, 'contains');
    }
    if (searchObj.sort != null) {
      pageQuery.addOnlyFilter('sort', searchObj.sort, 'contains');
    }
    if (searchObj.state != null) {
      pageQuery.addOnlyFilter('state', searchObj.state, 'contains');
    }
    if (searchObj.createTime != null) {
      pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'contains');
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
