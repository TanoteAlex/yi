import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SUCCESS } from '../../../models/constants.model';
import { AreaColumnService } from '../../../services/area-column.service';
import { AreaColumnListVo } from '../../../models/domain/listVo/area-column-list-vo.model';
import { PageQuery } from '../../../models/page-query.model';
import { SalesAreaService } from '../../../services/sales-area.service';
import { SalesAreaListVo } from '../../../models/domain/listVo/sales-area-list-vo.model';

@Component({
  selector: 'list-area-column',
  templateUrl: './list-area-column.component.html',
  styleUrls: ['./list-area-column.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [SalesAreaService],
})
export class ListAreaColumnComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  areaColumns: Array<AreaColumnListVo>;

  salesAreaPageQuery: PageQuery = new PageQuery();

  salesAreas: Array<SalesAreaListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private areaColumnService: AreaColumnService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService, private salesAreaService: SalesAreaService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
    this.initSalesArea();
  }

  initSalesArea(){
    this.salesAreaService.query(this.salesAreaPageQuery).subscribe(response => {
      this.salesAreas = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }

  buildForm() {
    this.searchForm = this.fb.group({
      salesArea: [null],
      title: [null],
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
    this.areaColumnService.query(this.pageQuery).subscribe(response => {
      this.areaColumns = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      salesArea: null,
      title: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(areaColumnId) {
    this.areaColumnService.removeById(areaColumnId).subscribe(response => {
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

    if (searchObj.salesArea != null) {
      pageQuery.addOnlyFilter('salesArea.id', searchObj.salesArea.id, 'eq');
    }
    if (searchObj.title != null) {
      pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
    }

    return pageQuery;
  }


}
