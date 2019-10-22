import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {UserService} from '../../../services/user.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListUserComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private userService: UserService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({

      deptName: [null],
      username: [null],

      fullName: [null],

      jobNo: [null],

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
    this.userService.query(this.pageQuery).subscribe(response => {
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

      deptName: null,
      username: null,

      fullName: null,

      jobNo: null,

    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(userId) {
    this.userService.removeById(userId).subscribe(response => {
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

    if (searchObj.deptName != null) {
      pageQuery.addOnlyFilter('dept.deptName', searchObj.deptName, 'contains');
    }
    if (searchObj.username != null) {
      pageQuery.addOnlyFilter('username', searchObj.username, 'contains');
    }

    if (searchObj.fullName != null) {
      pageQuery.addOnlyFilter('fullName', searchObj.fullName, 'contains');
    }

    if (searchObj.jobNo != null) {
      pageQuery.addOnlyFilter('jobNo', searchObj.jobNo, 'contains');
    }

    return pageQuery;
  }


  /**
   * 禁用
   * @param id
   */
  disable(id) {
    this.userService.disable(id).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("禁用成功")
        this.getData()
      } else {
        this.msg.error("请求失败" + response.message);
      }
    }, error => {
      this.msg.error("请求失败" + error.message);
    })
  }

  /**
   *启用
   * @param id
   */
  enable(id) {
    this.userService.enable(id).subscribe(e => {
      if (e.result == SUCCESS) {
        this.msg.success("启用成功")
        this.getData()
      } else {
        this.msg.error("请求失败" + e.message);
      }
    }, error => {
      this.msg.error("请求失败" + error.message);
    })
  }


}
