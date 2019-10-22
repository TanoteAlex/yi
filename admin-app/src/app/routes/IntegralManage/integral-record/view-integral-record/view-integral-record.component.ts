

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { IntegralRecordService } from "../../../services/integral-record.service";
import { IntegralRecord } from "../../../models/original/integral-record.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-integral-record',
  templateUrl: './view-integral-record.component.html',
  styleUrls: ['./view-integral-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewIntegralRecordComponent implements OnInit {

integralRecord: IntegralRecord=new IntegralRecord;

    constructor(private route: ActivatedRoute,private location: Location,private integralRecordService: IntegralRecordService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.integralRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.integralRecord = response.data;
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
