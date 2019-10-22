import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CouponService} from '../../../services/coupon.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-coupon',
  templateUrl: './list-storage-volume.component.html',
  styleUrls: ['./list-storage-volume.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListStorageVolumeComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private couponService: CouponService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.pageQuery.addOnlyFilter('couponType', 3, 'eq');
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      couponName: [null],
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
    this.pageQuery.addOnlyFilter('couponType', 3, 'eq');
    this.couponService.query(this.pageQuery).subscribe(response => {
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
      couponName: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(couponId) {
    this.couponService.removeById(couponId).subscribe(response => {
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
    if (searchObj.couponName != null) {
      pageQuery.addOnlyFilter('couponName', searchObj.couponName, 'contains');
    }
    return pageQuery;
  }


}
