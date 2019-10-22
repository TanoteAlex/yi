

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import { CouponGrantRecordService } from "../../../services/coupon-grant-record.service";
import { CouponGrantRecordVo } from '../../../models/domain/vo/coupon-grant-record-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-voucher-grant-record',
  templateUrl: './view-voucher-grant-record.component.html',
  styleUrls: ['./view-voucher-grant-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewVoucherGrantRecordComponent implements OnInit {

    couponGrantRecord: CouponGrantRecordVo=new CouponGrantRecordVo;

    constructor(private route: ActivatedRoute,private location: Location,private couponGrantRecordService: CouponGrantRecordService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponGrantRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.couponGrantRecord = response.data;
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
