import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {NationalGroupRecordService} from '../../../services/national-group-record.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-national-group-record',
  templateUrl: './list-national-group-record.component.html',
  styleUrls: ['./list-national-group-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListNationalGroupRecordComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private nationalGroupRecordService: NationalGroupRecordService, public msg: NzMessageService,
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
      nationalGroup: [null],
      groupCode: [null],
      member: [null],
      groupPeople: [null],
      joinPeople: [null],
      openTime: [null],
      consignee: [null],
      consigneePhone: [null],
      consigneeAddr: [null],
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
    this.nationalGroupRecordService.query(this.pageQuery).subscribe(response => {
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
      nationalGroup: null,
      groupCode: null,
      member: null,
      groupPeople: null,
      joinPeople: null,
      openTime: null,
      consignee: null,
      consigneePhone: null,
      consigneeAddr: null,
      state: null,
      createTime: null,
      deleted: null,
      delTime: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
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
    if (searchObj.nationalGroup != null) {
      pageQuery.addOnlyFilter('nationalGroup', searchObj.nationalGroup, 'contains');
    }
    if (searchObj.groupCode != null) {
      pageQuery.addOnlyFilter('groupCode', searchObj.groupCode, 'contains');
    }
    if (searchObj.member != null) {
      pageQuery.addOnlyFilter('member', searchObj.member, 'contains');
    }
    if (searchObj.groupPeople != null) {
      pageQuery.addOnlyFilter('groupPeople', searchObj.groupPeople, 'contains');
    }
    if (searchObj.joinPeople != null) {
      pageQuery.addOnlyFilter('joinPeople', searchObj.joinPeople, 'contains');
    }
    if (searchObj.openTime != null) {
      pageQuery.addOnlyFilter('openTime', searchObj.openTime, 'contains');
    }
    if (searchObj.consignee != null) {
      pageQuery.addOnlyFilter('consignee', searchObj.consignee, 'contains');
    }
    if (searchObj.consigneePhone != null) {
      pageQuery.addOnlyFilter('consigneePhone', searchObj.consigneePhone, 'contains');
    }
    if (searchObj.consigneeAddr != null) {
      pageQuery.addOnlyFilter('consigneeAddr', searchObj.consigneeAddr, 'contains');
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
