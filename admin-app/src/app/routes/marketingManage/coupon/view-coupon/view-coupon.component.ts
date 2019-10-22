import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {CouponService} from "../../../services/coupon.service";
import {Coupon} from "../../../models/original/coupon.model";
import {Location} from "@angular/common";
import {PageQuery} from "../../../models/page-query.model";
import {FormGroup} from "@angular/forms";
import {CouponReceiveService} from "../../../services/coupon-receive.service";

@Component({
  selector: 'view-coupon',
  templateUrl: './view-coupon.component.html',
  styleUrls: ['./view-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCouponComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  loading = false;

  datas: any[] = [];

  coupon: Coupon = new Coupon;

  couponId;

  constructor(private route: ActivatedRoute, private location: Location, private couponService: CouponService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, public couponReceiveService: CouponReceiveService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
      this.pageQuery.addOnlyFilter("coupon.id",this.couponId,"eq");
    });
    this.getData();
  }

  getById(objId) {
    this.couponId = objId
    this.couponService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.coupon = response.data;
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

  getData() {
    this.loading = true;
    this.couponReceiveService.query(this.pageQuery).subscribe(response => {
      this.datas = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  searchData(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
    this.getData();
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  goBack() {
    this.location.back();
  }

}
