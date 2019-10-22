import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {ArticleService} from '../../../services/article.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-article',
    templateUrl: './list-article.component.html',
    styleUrls: ['./list-article.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListArticleComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private articleService: ArticleService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    menus = [
        { name: "全部专题", value: "" },
        { name: "显示中", value: 0 },
        { name: "已下架", value: 1 },
    ];

    onItemClick(i) {
//        this.pageQuery.clearFilter();
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.getData();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            title: [null],
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
        this.articleService.query(this.pageQuery).subscribe(response => {
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
            title: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(articleId) {
        this.articleService.removeById(articleId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }

    upShelf(articleId) {
        this.articleService.upOrDownShelf(articleId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("上架成功");
                /*this.router.navigate(['/pages/member/list']);*/
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }


    downShelf(articleId) {
        this.articleService.upOrDownShelf(articleId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("下架成功");
                /*this.router.navigate(['/pages/member/list']);*/
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求失败");
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.title != null) {
            pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
        }
        return pageQuery;
    }


}
