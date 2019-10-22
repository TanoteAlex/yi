import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {PositionService} from '../../../services/position.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-position',
  templateUrl: './list-position.component.html',
  styleUrls: ['./list-position.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListPositionComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private positionService: PositionService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  menus = [
    {name: "全部", value: ""},
    {name: "启用", value: 0},
    {name: "禁用", value: 1},
  ];

  onItemClick(i) {
//    this.pageQuery.clearFilter();
    this.pageQuery.resetPage();
    this.configPageQuery(this.pageQuery);
    this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
    this.pageQuery.addLockFilterName('state');
    this.getData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      name: [null],
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
    this.positionService.query(this.pageQuery).subscribe(response => {
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
      name: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;
    if (searchObj.name != null) {
      pageQuery.addOnlyFilter('name', searchObj.name, 'contains');
    }
    return pageQuery;
  }

  remove(recommendId) {
    this.positionService.removeById(recommendId).subscribe(response => {
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

  chooseSwitch(i) {
    let state =  this.datas[i].state, id =  this.datas[i].id;
    if (state == 0) {
      //禁用
      this.positionService.updateStateDisable(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("禁用成功");
          this.datas[i].state=1
        } else {
          this.msg.error('请求失败' + response.message);
        }
      }, error => {
        this.msg.error('请求错误' + error.message);
      });
    } else if (state == 1) {
      //启用
      this.positionService.updateStateEnable(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("启用成功");
          this.datas[i].state=0
        } else {
          this.msg.error('请求失败' + response.message);
        }
      }, error => {
        this.msg.error('请求错误' + error.message);
      });
    }
  }

}
