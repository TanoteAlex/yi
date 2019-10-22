import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {CouponService} from "../../../services/coupon.service";
import {Coupon} from "../../../models/original/coupon.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-coupon',
  templateUrl: './view-coupon.component.html',
  styleUrls: ['./view-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCouponComponent implements OnInit {

coupon: Coupon=new Coupon;

    constructor(private route: ActivatedRoute,private location: Location,private couponService: CouponService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
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

    goBack(){
        this.location.back();
    }

}
