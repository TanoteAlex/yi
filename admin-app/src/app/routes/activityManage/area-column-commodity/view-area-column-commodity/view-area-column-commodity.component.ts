

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS } from '../../../models/constants.model';
import { AreaColumnCommodityService } from "../../../services/area-column-commodity.service";
import { AreaColumnCommodityVo } from '../../../models/domain/vo/area-column-commodity-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-area-column-commodity',
  templateUrl: './view-area-column-commodity.component.html',
  styleUrls: ['./view-area-column-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAreaColumnCommodityComponent implements OnInit {

    areaColumnCommodity: AreaColumnCommodityVo=new AreaColumnCommodityVo;

    constructor(private route: ActivatedRoute,private location: Location,private areaColumnCommodityService: AreaColumnCommodityService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
      this.route.queryParams.subscribe(params => {
        this.getById(params);
      });
    }

    getById(objId){
        this.areaColumnCommodityService.postData("getVoById",objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.areaColumnCommodity = response.data;
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
