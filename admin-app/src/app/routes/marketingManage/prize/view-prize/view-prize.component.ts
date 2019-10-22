

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import { PrizeService } from "../../../services/prize.service";
import { PrizeVo } from '../../../models/domain/vo/prize-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-prize',
  templateUrl: './view-prize.component.html',
  styleUrls: ['./view-prize.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewPrizeComponent implements OnInit {

    prize: PrizeVo=new PrizeVo;

    constructor(private route: ActivatedRoute,private location: Location,private prizeService: PrizeService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.prizeService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.prize = response.data;
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
