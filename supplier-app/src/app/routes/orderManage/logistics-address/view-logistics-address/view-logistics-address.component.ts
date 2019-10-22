

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { LogisticsAddressService } from "../../../services/logistics-address.service";
import { LogisticsAddress } from "../../../models/original/logistics-address.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-logistics-address',
  templateUrl: './view-logistics-address.component.html',
  styleUrls: ['./view-logistics-address.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewLogisticsAddressComponent implements OnInit {

logisticsAddress: LogisticsAddress=new LogisticsAddress;

    constructor(private route: ActivatedRoute,private location: Location,private logisticsAddressService: LogisticsAddressService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.logisticsAddressService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.logisticsAddress = response.data;
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
