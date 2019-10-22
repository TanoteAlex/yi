

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SalesAreaCommodityService } from "../../../services/sales-area-commodity.service";
import { SalesAreaCommodityVo } from '../../../models/domain/vo/sales-area-commodity-vo.model';
import { Location } from "@angular/common";
import { SUCCESS } from '../../../models/constants.model';

@Component({
  selector: 'view-sales-area-commodity',
  templateUrl: './view-sales-area-commodity.component.html',
  styleUrls: ['./view-sales-area-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSalesAreaCommodityComponent implements OnInit {

    salesAreaCommodity: SalesAreaCommodityVo=new SalesAreaCommodityVo;

    constructor(private route: ActivatedRoute,private location: Location,private salesAreaCommodityService: SalesAreaCommodityService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
      this.route.queryParams.subscribe((params: ParamMap) => {
          this.getById(params);
        });
    }

    getById(objId){
        this.salesAreaCommodityService.postData("getVoById",objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.salesAreaCommodity = response.data;
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
