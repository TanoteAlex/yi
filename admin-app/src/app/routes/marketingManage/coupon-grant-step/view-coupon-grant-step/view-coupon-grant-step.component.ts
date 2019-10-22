

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import { CouponGrantStepService } from "../../../services/coupon-grant-step.service";
import { CouponGrantStepVo } from '../../../models/domain/vo/coupon-grant-step-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-coupon-grant-step',
  templateUrl: './view-coupon-grant-step.component.html',
  styleUrls: ['./view-coupon-grant-step.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCouponGrantStepComponent implements OnInit {

    couponGrantStep: CouponGrantStepVo=new CouponGrantStepVo;

    constructor(private route: ActivatedRoute,private location: Location,private couponGrantStepService: CouponGrantStepService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponGrantStepService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.couponGrantStep = response.data;
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
