

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { BasicRuleService } from "../../../services/basic-rule.service";
import { BasicRule } from "../../../models/original/basic-rule.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-basic-rule',
  templateUrl: './view-basic-rule.component.html',
  styleUrls: ['./view-basic-rule.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewBasicRuleComponent implements OnInit {

basicRule: BasicRule=new BasicRule;

    constructor(private route: ActivatedRoute,private location: Location,private basicRuleService: BasicRuleService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.basicRuleService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.basicRule = response.data;
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
