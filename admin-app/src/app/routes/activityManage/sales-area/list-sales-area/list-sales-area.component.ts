import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SUCCESS } from '../../../models/constants.model';
import { PageQuery } from '../../../models/page-query.model';
import { SalesAreaService } from '../../../services/sales-area.service';
import { SalesAreaListVo } from '../../../models/domain/listVo/sales-area-list-vo.model';

@Component({
  selector: 'list-sales-area',
  templateUrl: './list-sales-area.component.html',
  styleUrls: ['./list-sales-area.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ListSalesAreaComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  salesAreas: Array<SalesAreaListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private salesAreaService: SalesAreaService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      title: [null],
      salesArea: [null],
    });
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value);
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
    this.salesAreaService.query(this.pageQuery).subscribe(response => {
      this.salesAreas = response['content'];
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
      salesArea: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(salesAreaId) {
    this.salesAreaService.removeById(salesAreaId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success('删除成功');
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

    if (searchObj.title != null) {
      pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
    }
    if (searchObj.salesArea != null) {
      pageQuery.addOnlyFilter('salesArea.id', searchObj.salesArea.id, 'eq');
    }

    return pageQuery;
  }


}
