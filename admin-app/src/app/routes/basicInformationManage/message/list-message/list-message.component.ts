import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {MessageService} from '../../../services/message.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-message',
    templateUrl: './list-message.component.html',
    styleUrls: ['./list-message.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListMessageComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    indeterminate = false;

    constructor(public route: ActivatedRoute, public router: Router, private messageService: MessageService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    chooseSwitch(i) {
        let state = this.datas[i].state, id = this.datas[i].id;
        if (state == 0) {
            //隐藏
            this.messageService.updateState(id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msg.success("隐藏成功");
                    this.datas[i].state = 1
                } else {
                    this.msg.error(response.message ? response.message : "请求失败");
                }
            }, error => {
                this.msg.error(error.message ? error.message : "请求错误");
            });
        } else if (state == 1) {
            //显示
            this.messageService.updateState(id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msg.success("显示成功");
                    this.datas[i].state = 0
                } else {
                    this.msg.error(response.message ? response.message : "请求失败");
                }
            }, error => {
                this.msg.error(error.message ? error.message : "请求错误");
            });
        }
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            title: [null],
            content: [null],
            messageType: [null],
            sort: [null],
            state: [null],
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
        this.messageService.query(this.pageQuery).subscribe(response => {
            this.datas = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message ? error.message : "请求错误");
        });
    }

    clearSearch() {
        this.searchForm.reset({
            title: null,
            content: null,
            messageType: null,
            sort: null,
            state: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(messageId) {
        this.messageService.removeById(messageId).subscribe(response => {
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

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.title != null) {
            pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
        }
        if (searchObj.content != null) {
            pageQuery.addOnlyFilter('content', searchObj.content, 'contains');
        }
        if (searchObj.state != null) {
            pageQuery.addOnlyFilter('state', searchObj.state, 'contains');
        }
        return pageQuery;
    }

}
