import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {LoginRecordService} from '../../../services/login-record.service';
import {LoginRecordListVo} from '../../../models/domain/listVo/login-record-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-login-record',
  templateUrl: './list-login-record.component.html',
  styleUrls: ['./list-login-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListLoginRecordComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  loginRecords: Array<LoginRecordListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private loginRecordService: LoginRecordService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      memberName: [null],
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
    this.loginRecordService.query(this.pageQuery).subscribe(response => {
      this.loginRecords = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      memberName: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(loginRecordId) {
    this.loginRecordService.removeById(loginRecordId).subscribe(response => {
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
    if (searchObj.memberName != null) {
      pageQuery.addOnlyFilter('member.nickname', searchObj.memberName, 'contains');
    }
    return pageQuery;
  }


}
