

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { DistributionLevelService } from "../../../services/distribution-level.service";
import { DistributionLevelVo } from '../../../models/domain/vo/distribution-level-vo.model';
import { Location } from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-distribution-level',
  templateUrl: './view-distribution-level.component.html',
  styleUrls: ['./view-distribution-level.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewDistributionLevelComponent implements OnInit {

    distributionLevel: DistributionLevelVo=new DistributionLevelVo;

    constructor(private route: ActivatedRoute,private location: Location,private distributionLevelService: DistributionLevelService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.distributionLevelService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.distributionLevel = response.data;
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
