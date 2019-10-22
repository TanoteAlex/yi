import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SUCCESS } from '../../../models/constants.model';
import { AreaColumnCommodityService } from '../../../services/area-column-commodity.service';
import { AreaColumnCommodityListVo } from '../../../models/domain/listVo/area-column-commodity-list-vo.model';
import { PageQuery } from '../../../models/page-query.model';
import { AreaColumnService } from '../../../services/area-column.service';
import { SalesAreaListVo } from '../../../models/domain/listVo/sales-area-list-vo.model';
import { AreaColumnListVo } from '../../../models/domain/listVo/area-column-list-vo.model';

@Component({
  selector: 'list-area-column-commodity',
  templateUrl: './list-area-column-commodity.component.html',
  styleUrls: ['./list-area-column-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [AreaColumnService],
})
export class ListAreaColumnCommodityComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  areaColumnCommoditys: Array<AreaColumnCommodityListVo>;

  areaColumnPageQuery: PageQuery = new PageQuery();

  areaColumns: Array<AreaColumnListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private areaColumnCommodityService: AreaColumnCommodityService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService, private areaColumnService: AreaColumnService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
    this.initAreaColumns();
  }

  initAreaColumns(){
    this.areaColumnService.query(this.areaColumnPageQuery).subscribe(response => {
      this.areaColumns = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }

  buildForm() {
    this.searchForm = this.fb.group({
      areaColumn: [null],
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
    this.areaColumnCommodityService.query(this.pageQuery).subscribe(response => {
      this.areaColumnCommoditys = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      areaColumn: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(areaColumnCommodityId) {
    this.areaColumnCommodityService.postData("removeById",areaColumnCommodityId).subscribe(response => {
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
    if (searchObj.areaColumn != null) {
      pageQuery.addOnlyFilter('areaColumn.id', searchObj.areaColumn.id, 'eq');
    }
    return pageQuery;
  }


}
