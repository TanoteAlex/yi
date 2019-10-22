import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {OrderSettingService} from '../../../services/order-setting.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'list-order-setting',
    templateUrl: './list-order-setting.component.html',
    styleUrls: ['./list-order-setting.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListOrderSettingComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    // orderSetUpFrom:FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, private orderSettingService: OrderSettingService, public msg: NzMessageService,
        private fb: FormBuilder,private modalService: NzModalService) {
      this.buildForm();
    }

  buildForm() {
    this.searchForm = this.fb.group({
      setType: [null],
      timeout: [null],
      day: [null],
      hour: [null],
      minute: [null],
      createTime: [null],
    });
  }

    ngOnInit() {
      this.searchData();
    }

  searchData(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
    // this.configPageQuery(this.pageQuery);
    this.getData();
  }

  getData() {
    this.loading = true;
    this.orderSettingService.query(this.pageQuery).subscribe(response => {
      this.datas=response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误'+ error.message);
    });
  }

  updateTimeoutValue(id,updateMinute,timeout){
    if(updateMinute==timeout){
      return
    }
    this.orderSettingService.updateTimeoutValue(id,updateMinute).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("修改成功");
        this.getData();
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

}
