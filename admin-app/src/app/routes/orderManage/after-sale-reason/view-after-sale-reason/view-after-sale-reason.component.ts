

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { AfterSaleReasonService } from "../../../services/after-sale-reason.service";
import { AfterSaleReasonVo } from '../../../models/domain/vo/after-sale-reason-vo.model';
import { Location } from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-after-sale-reason',
  templateUrl: './view-after-sale-reason.component.html',
  styleUrls: ['./view-after-sale-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAfterSaleReasonComponent implements OnInit {

    afterSaleReason: AfterSaleReasonVo=new AfterSaleReasonVo;

    constructor(private route: ActivatedRoute,private location: Location,private afterSaleReasonService: AfterSaleReasonService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.afterSaleReasonService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.afterSaleReason = response.data;
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
