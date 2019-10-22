import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CommentService} from '../../../services/comment.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-comment',
  templateUrl: './list-comment.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ListCommentComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private commentService: CommentService, public msg: NzMessageService, private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      commodity: {
        id: null,
        commodityName: null,
      },
      commodityName: [null],
      member: {
        id: null,
        username: null
      },
      username: [null],
    });
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  reply() {
    this.getData();
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
    this.commentService.query(this.pageQuery).subscribe(response => {
      if (this.pageQuery.page != 1 && response['content'] == "") {
        this.pageQuery.page = this.pageQuery.page - 1;
        this.getData();
      } else {
        this.datas = response['content'];
        this.pageQuery.covertResponses(response);
        this.loading = false;
      }
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      commodity: {
        id: null,
        commodityName: null,
      },
      commodityName: null,
      member: {
        id: null,
        username: null
      },
      username: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(commentId) {
    this.commentService.removeById(commentId).subscribe(response => {
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

  chooseSwitch(i) {
    let state = this.datas[i].display, id = this.datas[i].id;
    if (state == 1) {
      //显示
      this.commentService.display(id, 0).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("显示成功");
          this.datas[i].display = 0
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    } else if (state == 0) {
      //取消显示
      this.commentService.hide(id, 1).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("取消显示成功");
          this.datas[i].display = 1
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    }
  }

  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;
    if (searchObj.commodityName != null) {
      pageQuery.addOnlyFilter('commodity.commodityName', searchObj.commodityName, 'contains');
    }
    if (searchObj.username != null) {
      pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
    }
    return pageQuery;
  }


}
