

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { LoginRecordService } from "../../../services/login-record.service";
import { LoginRecordVo } from '../../../models/domain/vo/login-record-vo.model';
import { Location } from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-login-record',
  templateUrl: './view-login-record.component.html',
  styleUrls: ['./view-login-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewLoginRecordComponent implements OnInit {

    loginRecord: LoginRecordVo=new LoginRecordVo;

    constructor(private route: ActivatedRoute,private location: Location,private loginRecordService: LoginRecordService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.loginRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.loginRecord = response.data;
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
