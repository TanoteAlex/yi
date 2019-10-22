import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {IntegralRecordService} from '../../../services/integral-record.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-integral-record',
  templateUrl: './list-integral-record.component.html',
  styleUrls: ['./list-integral-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListIntegralRecordComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private integralRecordService: IntegralRecordService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  menus = [
    {name: "签到", value: 1},
    {name: "邀请好友", value: 2},
    {name: "评论", value: 3},
  ];

  buildForm() {
    this.searchForm = this.fb.group({
      username: [null],
      memberName: [null],
      taskName: [null],
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
    this.integralRecordService.query(this.pageQuery).subscribe(response => {
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
      username: null,
      memberName: null,
      taskName: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }


  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;
    if (searchObj.username != null) {
      pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
    }
    if (searchObj.memberName != null) {
      pageQuery.addOnlyFilter('member.nickname', searchObj.memberName, 'contains');
    }
    if (searchObj.taskName != null) {
      pageQuery.addOnlyFilter('taskName', searchObj.taskName, 'contains');
    }
    return pageQuery;
  }


}
