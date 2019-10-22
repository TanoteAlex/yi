import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierAccountService} from '../../../services/supplier-account.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-supplier-account',
  templateUrl: './list-supplier-account.component.html',
  styleUrls: ['./list-supplier-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListSupplierAccountComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private supplierAccountService: SupplierAccountService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      id: [null],
      guid: [null],
      supplier: [null],
      amount: [null],
      balance: [null],
      freezeAmount: [null],
      withdrawAmount: [null],
      createTime: [null],
      remark: [null],
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
    this.supplierAccountService.query(this.pageQuery).subscribe(response => {
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
      id: null,
      guid: null,
      supplier: null,
      amount: null,
      balance: null,
      freezeAmount: null,
      withdrawAmount: null,
      createTime: null,
      remark: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(supplierAccountId) {
    this.supplierAccountService.removeById(supplierAccountId).subscribe(response => {
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
    if (searchObj.id != null) {
      pageQuery.addOnlyFilter('id', searchObj.id, 'contains');
    }
    if (searchObj.guid != null) {
      pageQuery.addOnlyFilter('guid', searchObj.guid, 'contains');
    }
    if (searchObj.supplier != null) {
      pageQuery.addOnlyFilter('supplier', searchObj.supplier, 'contains');
    }
    if (searchObj.amount != null) {
      pageQuery.addOnlyFilter('amount', searchObj.amount, 'contains');
    }
    if (searchObj.balance != null) {
      pageQuery.addOnlyFilter('balance', searchObj.balance, 'contains');
    }
    if (searchObj.freezeAmount != null) {
      pageQuery.addOnlyFilter('freezeAmount', searchObj.freezeAmount, 'contains');
    }
    if (searchObj.withdrawAmount != null) {
      pageQuery.addOnlyFilter('withdrawAmount', searchObj.withdrawAmount, 'contains');
    }
    if (searchObj.createTime != null) {
      pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'contains');
    }
    if (searchObj.remark != null) {
      pageQuery.addOnlyFilter('remark', searchObj.remark, 'contains');
    }
    return pageQuery;
  }


}
