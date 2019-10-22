import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {CouponGrantStepService} from '../../../services/coupon-grant-step.service';
import {CouponGrantStepListVo} from '../../../models/domain/listVo/coupon-grant-step-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";

@Component({
  selector: 'list-coupon-grant-step',
  templateUrl: './list-coupon-grant-step.component.html',
  styleUrls: ['./list-coupon-grant-step.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListCouponGrantStepComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  couponGrantSteps: Array<CouponGrantStepListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private couponGrantStepService: CouponGrantStepService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      grantNode: [null],
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
    this.couponGrantStepService.query(this.pageQuery).subscribe(response => {
      this.couponGrantSteps = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      grantNode: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(couponGrantStepId) {
    this.couponGrantStepService.removeById(couponGrantStepId).subscribe(response => {
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
    if (searchObj.grantNode != null) {
      pageQuery.addOnlyFilter('grantNode', searchObj.grantNode, 'eq');
    }
    return pageQuery;
  }

  grantNodes = [
    {id: 1, name: "下单"},
    {id: 2, name: "收货"},
    {id: 3, name: "评论"},
    {id: 4, name: "超过三包时效"},
  ]

}
