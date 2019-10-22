

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { FreightTemplateConfigService } from "../../../services/freight-template-config.service";
import { Location } from "@angular/common";
import {FreightTemplateConfig} from "../../../models/original/freight-template-config.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-freight-template-config',
  templateUrl: './view-freight-template-config.component.html',
  styleUrls: ['./view-freight-template-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewFreightTemplateConfigComponent implements OnInit {

    freightTemplateConfig: FreightTemplateConfig=new FreightTemplateConfig;

    constructor(private route: ActivatedRoute,private location: Location,private freightTemplateConfigService: FreightTemplateConfigService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.freightTemplateConfigService.getByIdForSupplier(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.freightTemplateConfig = response.data;
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
