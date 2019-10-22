import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {ArticleCommentService} from '../../../services/article-comment.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-article-comment',
  templateUrl: './list-article-comment.component.html',
  styleUrls: ['./list-article-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListArticleCommentComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private articleCommentService: ArticleCommentService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      articleId: [null],
      commentator: [null],

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
    this.articleCommentService.query(this.pageQuery).subscribe(response => {
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
      articleId: null,
      commentator: null,

    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(articleCommentId) {

    this.articleCommentService.removeById(articleCommentId).subscribe(response => {
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

    if (searchObj.articleId != null) {
      pageQuery.addOnlyFilter('article.title', searchObj.articleId, 'contains');
    }
    if (searchObj.commentator != null) {
      pageQuery.addOnlyFilter('commentator', searchObj.commentator, 'contains');
    }

    return pageQuery;
  }

  chooseSwitch(i) {
    let state = this.datas[i].state, id = this.datas[i].id;
    if (state == 1) {
      //显示
      this.articleCommentService.display(id, 0).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("显示成功");
          this.datas[i].state = 0
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    } else if (state == 0) {
      //取消显示
      this.articleCommentService.hide(id, 1).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("取消显示成功");
          this.datas[i].state = 1
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    }
  }

  reply() {
    this.getData();
  }


}
