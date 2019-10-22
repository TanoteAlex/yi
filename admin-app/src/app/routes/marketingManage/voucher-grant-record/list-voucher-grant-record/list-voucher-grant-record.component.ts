import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {CouponGrantRecordService} from '../../../services/coupon-grant-record.service';
import {CouponGrantRecordListVo} from '../../../models/domain/listVo/coupon-grant-record-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";

@Component({
  selector: 'list-voucher-grant-record',
  templateUrl: './list-voucher-grant-record.component.html',
  styleUrls: ['./list-voucher-grant-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListVoucherGrantRecordComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  couponGrantRecords: Array<CouponGrantRecordListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private couponGrantRecordService: CouponGrantRecordService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      nickname: [null],
      couponName: [null],
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
    this.couponGrantRecordService.query(this.pageQuery).subscribe(response => {
      this.couponGrantRecords = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      nickname: null,
      couponName: null,
      grantNode: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(couponGrantRecordId) {
    this.couponGrantRecordService.removeById(couponGrantRecordId).subscribe(response => {
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
    if (searchObj.nickname != null) {
      pageQuery.addOnlyFilter('member.nickname', searchObj.nickname, 'contains');
    }
    if (searchObj.couponName != null) {
      pageQuery.addOnlyFilter('coupon.couponName', searchObj.couponName, 'contains');
    }
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
