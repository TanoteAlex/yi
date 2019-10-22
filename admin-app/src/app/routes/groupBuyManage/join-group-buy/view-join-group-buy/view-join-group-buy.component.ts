

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { JoinGroupBuyService } from "../../../services/join-group-buy.service";
import { JoinGroupBuyVo } from '../../../models/domain/vo/join-group-buy-vo.model';
import { Location } from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-join-group-buy',
  templateUrl: './view-join-group-buy.component.html',
  styleUrls: ['./view-join-group-buy.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewJoinGroupBuyComponent implements OnInit {

    joinGroupBuy: JoinGroupBuyVo=new JoinGroupBuyVo;

    constructor(private route: ActivatedRoute,private location: Location,private joinGroupBuyService: JoinGroupBuyService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.joinGroupBuyService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.joinGroupBuy = response.data;
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
