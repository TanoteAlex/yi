

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SupplierAccountRecordService } from "../../../services/supplier-account-record.service";
import { Location } from "@angular/common";
import {SupplierAccountRecord} from "../../../models/original/supplier-account-record.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-supplier-account-record',
  templateUrl: './view-supplier-account-record.component.html',
  styleUrls: ['./view-supplier-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSupplierAccountRecordComponent implements OnInit {

    supplierAccountRecord: SupplierAccountRecord=new SupplierAccountRecord;

    constructor(private route: ActivatedRoute,private location: Location,private supplierAccountRecordService: SupplierAccountRecordService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierAccountRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierAccountRecord = response.data;
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
