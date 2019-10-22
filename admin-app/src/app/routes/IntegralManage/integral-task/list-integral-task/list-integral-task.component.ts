import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {DailyTaskService} from '../../../services/daily-task.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {IntegralTaskService} from "../../../services/integral-task.service";

@Component({
  selector: 'list-daily-task',
  templateUrl: './list-integral-task.component.html',
  styleUrls: ['./list-integral-task.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListIntegralTaskComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private integralTaskService: IntegralTaskService,public msg: NzMessageService,
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
      taskName: [null],
      growthValue: [null],
      state: [null],
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
   // this.pageQuery.addFilter("rangeType",1,"eq")
    this.integralTaskService.query(this.pageQuery).subscribe(response => {
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
      taskName: null,
      growthValue: null,
      state: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

    remove(dailyTaskId) {
      this.integralTaskService.removeById(dailyTaskId).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("删除成功");
          this.getData();
        }else{
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
    if (searchObj.taskName != null) {
      pageQuery.addOnlyFilter('taskName', searchObj.taskName, 'contains');
    }
    if (searchObj.growthValue != null) {
      pageQuery.addOnlyFilter('growthValue', searchObj.growthValue, 'contains');
    }
    if (searchObj.state != null) {
      pageQuery.addOnlyFilter('state', searchObj.state, 'contains');
    }
    return pageQuery;
  }

  chooseSwitch(i) {
    let state =  this.datas[i].state, id =  this.datas[i].id;
    if (state == 0) {
      //禁用
      this.integralTaskService.updateStates(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("禁用成功");
          this.datas[i].state=1
        } else {
          this.msg.error('请求失败' + response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    } else if (state == 1) {
      //启用
      this.integralTaskService.updateStates(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("启用成功");
          this.datas[i].state=0
        } else {
          this.msg.error('请求失败' + response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    }
  }


  updateGrowthValue(id, growthValue, thisgrowthValue) {
    if (growthValue == "") {
      this.getData();
      return
    }
    if (thisgrowthValue == growthValue) {
      return
    }
    this.integralTaskService.updateGrowthValue(id, growthValue).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("更新成功");
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

}
