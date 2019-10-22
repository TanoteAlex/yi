

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {GroupBuyOrderService} from "../../../services/group-buy-order.service";
import {GroupBuyOrderVo} from '../../../models/domain/vo/group-buy-order-vo.model';
import { Location } from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-group-buy-order',
  templateUrl: './view-group-buy-order.component.html',
  styleUrls: ['./view-group-buy-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewGroupBuyOrderComponent implements OnInit {

  groupBuyOrder: GroupBuyOrderVo=new GroupBuyOrderVo;

    constructor(private route: ActivatedRoute,private location: Location,private groupBuyOrderService: GroupBuyOrderService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.groupBuyOrderService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.groupBuyOrder = response.data;
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
