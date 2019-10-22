import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {DeptService} from '../../../services/dept.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-dept',
    templateUrl: './list-dept.component.html',
    styleUrls: ['./list-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListDeptComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private deptService: DeptService, public msg: NzMessageService,
        private fb: FormBuilder,private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        parentName:[null],
        deptNo:[null],
        deptName:[null],

        });
    }

    sort(sort: { key: string, value: string }): void {
        this.pageQuery.addSort(sort.key,sort.value)
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
        this.deptService.query(this.pageQuery).subscribe(response => {
            this.datas=response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误'+ error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({

        parentName:null,
        deptNo:null,
        deptName:null,

        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(deptId) {
                this.deptService.removeById(deptId).subscribe(response => {
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

            if (searchObj.parentName != null) {
                pageQuery.addOnlyFilter('parent.deptName', searchObj.parentName, 'contains');
            }
            if (searchObj.deptNo != null) {
                pageQuery.addOnlyFilter('deptNo', searchObj.deptNo, 'contains');
            }
            if (searchObj.deptName != null) {
                pageQuery.addOnlyFilter('deptName', searchObj.deptName, 'contains');
            }

        return pageQuery;
    }




}
