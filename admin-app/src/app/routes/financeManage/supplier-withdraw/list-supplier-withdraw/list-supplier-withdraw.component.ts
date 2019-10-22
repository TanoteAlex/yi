import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierWithdrawService} from '../../../services/supplier-withdraw.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-supplier-withdraw',
    templateUrl: './list-supplier-withdraw.component.html',
    styleUrls: ['./list-supplier-withdraw.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSupplierWithdrawComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    applyState = '';

    constructor(public route: ActivatedRoute, public router: Router, private supplierWithdrawService: SupplierWithdrawService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService) {
        this.buildForm();
    }

    menus = [
        { name: "全部", value: "" },
        { name: "待发放", value: "1" },
        { name: "已发放", value: "2" },
        { name: "发放异常", value: "3" }
    ];

    ngOnInit() {
        this.searchData();
    }

    onItemClick(i) {
//        this.pageQuery.clearFilter();
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyOneFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.applyState = this.menus[i].value;
        this.getData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            supplierName: [null],
            payee: [null],
            receiptsNo: [null],
            receiptsName: [null],
            applyAmount: [null],
            actualAmount: [null],
            serviceCharge: [null],
            drawee: [null],
            paymentsNo: [null],
            paymentsName: [null],
            paymentMethod: [null],
            state: [null],
            errorDesc: [null],
            applyTime: [null],
            grantTime: [null],
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
        this.supplierWithdrawService.query(this.pageQuery).subscribe(response => {
            this.datas = response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error(error.message ? error.message : '请求错误');
        });
    }

    clearSearch() {
        this.searchForm.reset({
            supplierName: null,
            payee: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(supplierWithdrawId) {
        this.supplierWithdrawService.removeById(supplierWithdrawId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("删除成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : '请求失败');
            }
        }, error => {
            this.msg.error(error.message ? error.message : '请求失败');
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;

        if (searchObj.supplierName != null) {
            pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'contains');
        }
        if (searchObj.payee != null) {
            pageQuery.addOnlyFilter('payee', searchObj.payee, 'contains');
        }
        return pageQuery;
    }

    /**
     * 替换字符
     * source 源字符串
     * replacementScheme 替换方案  [[targetStart,targetEnd,replacement],[targetStart,targetEnd,replacement]...]
     */
    replaceCharacter(source, replacementScheme) {
        if (source && replacementScheme) {
            let sourceArr = source.split('');
            sourceArr.forEach(function(item, index) {
                for (var scheme in replacementScheme) {
                    if (scheme) {
                        if (scheme[0] >= index && scheme[1] > index) {
                            item = scheme[2];
                        }
                    }
                }
            });
            return sourceArr.join('');
        }
        return source;
    }

    /**
     * 发放
     */
    grant(id) {
        this.supplierWithdrawService.grant(id).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("发放成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : '发放失败');
            }
        }, error => {
            this.msg.error(error.message ? error.message : '请求错误');
        });
    }

    /*
    * 驳回
    * */
    reject(id) {
        this.supplierWithdrawService.reject(id).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("驳回成功");
                this.getData();
            } else {
                this.msg.error(response.message ? response.message : '驳回失败');
            }
        }, error => {
            this.msg.error(error.message ? error.message : '请求错误');
        });
    }

}
