import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierCheckAccountService} from '../../../services/supplier-check-account.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-supplier-check-account',
  templateUrl: './list-supplier-check-account.component.html',
  styleUrls: ['./list-supplier-check-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListSupplierCheckAccountComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private supplierCheckAccountService: SupplierCheckAccountService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      supplierName: [null],
      productName: [null],
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
    this.supplierCheckAccountService.query(this.pageQuery).subscribe(response => {
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
      supplierName: null,
      productName: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(supplierCheckAccountId) {
    this.supplierCheckAccountService.removeById(supplierCheckAccountId).subscribe(response => {
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
    if (searchObj.supplierName != null) {
      pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'contains');
    }
    if (searchObj.productName != null) {
      pageQuery.addOnlyFilter('productName', searchObj.productName, 'contains');
    }
    return pageQuery;
  }


}
