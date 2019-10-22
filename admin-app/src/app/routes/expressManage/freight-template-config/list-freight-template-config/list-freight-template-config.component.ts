import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {FreightTemplateConfigService} from '../../../services/freight-template-config.service';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-freight-template-config',
  templateUrl: './list-freight-template-config.component.html',
  styleUrls: ['./list-freight-template-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListFreightTemplateConfigComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private freightTemplateConfigService: FreightTemplateConfigService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      configName: [null],
      freightType: [null],
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
    this.freightTemplateConfigService.query(this.pageQuery).subscribe(response => {
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
      configName: null,
      freightType: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(freightTemplateConfigId) {
    this.freightTemplateConfigService.removeById(freightTemplateConfigId).subscribe(response => {
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
    if (searchObj.configName != null) {
      pageQuery.addOnlyFilter('configName', searchObj.configName, 'contains');
    }
    if (searchObj.freightType != null) {
      pageQuery.addOnlyFilter('freightType', searchObj.freightType, 'eq');
    }
    return pageQuery;
  }

  freightTypes = [
    {name:"自定义运费",value:1},
    {name:"卖家承担运费",value:2},
  ];

}
