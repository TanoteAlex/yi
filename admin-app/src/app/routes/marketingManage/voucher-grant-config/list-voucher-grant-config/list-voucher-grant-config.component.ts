import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CouponGrantConfigService} from '../../../services/coupon-grant-config.service';
import {CouponGrantConfigListVo} from '../../../models/domain/listVo/coupon-grant-config-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-voucher-grant-config',
  templateUrl: './list-voucher-grant-config.component.html',
  styleUrls: ['./list-voucher-grant-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListVoucherGrantConfigComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  couponGrantConfigs: Array<CouponGrantConfigListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private couponGrantConfigService: CouponGrantConfigService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      grantStrategy: [null],
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
    this.couponGrantConfigService.query(this.pageQuery).subscribe(response => {
      this.couponGrantConfigs = response['content'];
      this.couponGrantConfigs.forEach(e=>{
        let dbCouponGrantSteps =[]
        e.couponGrantSteps.forEach(e1=>{
          dbCouponGrantSteps[e1.grantNode-1] = e1;
        })
        e.couponGrantSteps = dbCouponGrantSteps;
      })
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      grantStrategy: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(couponGrantConfigId) {
    this.couponGrantConfigService.removeById(couponGrantConfigId).subscribe(response => {
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
    if (searchObj.grantStrategy != null) {
      pageQuery.addOnlyFilter('grantStrategy', searchObj.grantStrategy, 'eq');
    }
    return pageQuery;
  }

  grantStrategys = [
    {id: 1, name: "一次发放"},
    {id: 2, name: "分批发放"},
  ]

  chooseSwitch(i) {
    let state =  this.couponGrantConfigs[i].state, id =  this.couponGrantConfigs[i].id;
    if (state == 0) {
      //禁用
      this.couponGrantConfigService.upAndDown(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("禁用成功");
          this.couponGrantConfigs[i].state=1
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    } else if (state == 1) {
      //启用
      this.couponGrantConfigService.upAndDown(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("启用成功");
          this.couponGrantConfigs[i].state=0
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    }
  }

}
