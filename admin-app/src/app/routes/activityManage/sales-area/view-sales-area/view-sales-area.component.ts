

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS } from '../../../models/constants.model';
import { SalesAreaService } from "../../../services/sales-area.service";
import { SalesAreaVo } from '../../../models/domain/vo/sales-area-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-sales-area',
  templateUrl: './view-sales-area.component.html',
  styleUrls: ['./view-sales-area.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSalesAreaComponent implements OnInit {

    salesArea: SalesAreaVo=new SalesAreaVo;

    constructor(private route: ActivatedRoute,private location: Location,private salesAreaService: SalesAreaService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.salesAreaService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.salesArea = response.data;
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
