import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {RegionService} from '../../../services/region.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-region',
  templateUrl: './list-region.component.html',
  styleUrls: ['./list-region.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListRegionComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private regionService: RegionService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({

      province: [null],
      city: [null],

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
    this.regionService.query(this.pageQuery).subscribe(response => {
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

      province: null,
      city: null,

    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(regionId) {
    this.regionService.removeById(regionId).subscribe(response => {
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

    if (searchObj.province != null) {
      pageQuery.addOnlyFilter('province', searchObj.province, 'contains');
    }
    if (searchObj.city != null) {
      pageQuery.addOnlyFilter('city', searchObj.city, 'contains');
    }

    return pageQuery;
  }


  disable(recommendId) {
    this.regionService.updateState(recommendId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("禁用成功");
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }


  enable(recommendId) {
    this.regionService.updateState(recommendId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("启用成功");
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }


}
