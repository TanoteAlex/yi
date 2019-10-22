

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS } from '../../../models/constants.model';
import { IntegralCashService } from "../../../services/integral-cash.service";
import { IntegralCashVo } from '../../../models/domain/vo/integral-cash-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-integral-cash',
  templateUrl: './view-integral-cash.component.html',
  styleUrls: ['./view-integral-cash.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewIntegralCashComponent implements OnInit {

    integralCash: IntegralCashVo=new IntegralCashVo;

    constructor(private route: ActivatedRoute,private location: Location,private integralCashService: IntegralCashService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.integralCashService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.integralCash = response.data;
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
