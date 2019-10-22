import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SUCCESS } from '../../../models/constants.model';
import { SalesAreaCommodityService } from '../../../services/sales-area-commodity.service';
import { SalesAreaCommodityListVo } from '../../../models/domain/listVo/sales-area-commodity-list-vo.model';
import { PageQuery } from '../../../models/page-query.model';
import { SalesAreaService } from '../../../services/sales-area.service';
import { ModalSelecetComponent } from '../../../components/modal-selecet/modal-selecet.component';
import { SalesAreaListVo } from '../../../models/domain/listVo/sales-area-list-vo.model';

@Component({
  selector: 'list-sales-area-commodity',
  templateUrl: './list-sales-area-commodity.component.html',
  styleUrls: ['./list-sales-area-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [SalesAreaService],
})
export class ListSalesAreaCommodityComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  salesAreaCommoditys: Array<SalesAreaCommodityListVo>;

  salesAreaPageQuery: PageQuery = new PageQuery();

  salesAreas: Array<SalesAreaListVo>;

  expandForm = false;

  @ViewChild('salesAreaModalSelect') salesAreaModalSelect: ModalSelecetComponent;

  constructor(public route: ActivatedRoute, public router: Router, private salesAreaCommodityService: SalesAreaCommodityService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService, public salesAreaService: SalesAreaService) {
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
      salesArea: [null]
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
    this.salesAreaCommodityService.query(this.pageQuery).subscribe(response => {
      this.salesAreaCommoditys = response['content'];
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
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(salesAreaCommodityId) {
    this.salesAreaCommodityService.postData("removeById",salesAreaCommodityId).subscribe(response => {
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

    return pageQuery;
  }

  setSalesArea(event) {
    if (event.title != null && event.length != 0) {
      this.searchForm.patchValue({
        salesArea: event,
      });
    }
  }
}
