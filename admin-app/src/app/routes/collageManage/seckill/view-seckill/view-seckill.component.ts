

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { SeckillService } from "../../../services/seckill.service";
import { Seckill } from "../../../models/original/seckill.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-seckill',
  templateUrl: './view-seckill.component.html',
  styleUrls: ['./view-seckill.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSeckillComponent implements OnInit {

seckill: Seckill=new Seckill;

    constructor(private route: ActivatedRoute,private location: Location,private seckillService: SeckillService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.seckillService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.seckill = response.data;
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
