

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { OrderLogService } from "../../../services/order-log.service";
import { OrderLog } from "../../../models/original/order-log.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-order-log',
  templateUrl: './view-order-log.component.html',
  styleUrls: ['./view-order-log.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewOrderLogComponent implements OnInit {

orderLog: OrderLog=new OrderLog;

    constructor(private route: ActivatedRoute,private location: Location,private orderLogService: OrderLogService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.orderLogService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.orderLog = response.data;
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
