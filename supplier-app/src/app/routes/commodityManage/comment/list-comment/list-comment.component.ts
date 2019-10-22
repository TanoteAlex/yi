import {Component, OnInit, ViewEncapsulation} from '@angular/core';
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

  constructor(public route: ActivatedRoute, public router: Router, private commentService: CommentService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
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

  replys: string[] = [""];

  /**
   * 回复
   */
  reply(id, e) {
    if (!e) {
      this.msg.warning("回复内容不能为空")
      return
    }
    this.commentService.reply(id, e).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("回复成功");
        /*  this.router.navigate(['/pages/comment/list']);*/
        this.getData();
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

  updateDispaly(id, display) {
    this.commentService.updateDispaly(id, display).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("取消成功");
        this.getData();
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
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
