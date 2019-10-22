

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import { CouponGrantConfigService } from "../../../services/coupon-grant-config.service";
import { CouponGrantConfigVo } from '../../../models/domain/vo/coupon-grant-config-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-voucher-grant-config',
  templateUrl: './view-voucher-grant-config.component.html',
  styleUrls: ['./view-voucher-grant-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewVoucherGrantConfigComponent implements OnInit {

    couponGrantConfig: CouponGrantConfigVo=new CouponGrantConfigVo;

    constructor(private route: ActivatedRoute,private location: Location,private couponGrantConfigService: CouponGrantConfigService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponGrantConfigService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.couponGrantConfig = response.data;
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
