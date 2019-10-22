import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SupplierAccountRecordService} from '../../../services/supplier-account-record.service';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-supplier-account-record',
  templateUrl: './list-supplier-account-record.component.html',
  styleUrls: ['./list-supplier-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListSupplierAccountRecordComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private supplierAccountRecordService: SupplierAccountRecordService, public msg: NzMessageService,
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
      operateType: [null],
      serialNo: [null],
      tradeAmount: [null],
      balance: [null],
      tradeType: [null],
      tradeTime: [null],
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
    this.supplierAccountRecordService.query(this.pageQuery).subscribe(response => {
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
      operateType: null,
      serialNo: null,
      tradeAmount: null,
      balance: null,
      tradeType: null,
      tradeTime: null,
      remark: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(supplierAccountRecordId) {
    this.supplierAccountRecordService.removeById(supplierAccountRecordId).subscribe(response => {
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
    if (searchObj.operateType != null) {
      pageQuery.addOnlyFilter('operateType', searchObj.operateType, 'contains');
    }
    if (searchObj.serialNo != null) {
      pageQuery.addOnlyFilter('serialNo', searchObj.serialNo, 'contains');
    }
    if (searchObj.tradeAmount != null) {
      pageQuery.addOnlyFilter('tradeAmount', searchObj.tradeAmount, 'contains');
    }
    if (searchObj.balance != null) {
      pageQuery.addOnlyFilter('balance', searchObj.balance, 'contains');
    }
    if (searchObj.tradeType != null) {
      pageQuery.addOnlyFilter('tradeType', searchObj.tradeType, 'contains');
    }
    if (searchObj.tradeTime != null) {
      pageQuery.addOnlyFilter('tradeTime', searchObj.tradeTime, 'contains');
    }
    if (searchObj.remark != null) {
      pageQuery.addOnlyFilter('remark', searchObj.remark, 'contains');
    }
    return pageQuery;
  }


}
