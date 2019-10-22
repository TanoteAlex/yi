

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { RebateGroupService } from "../../../services/rebate-group.service";
import { RebateGroup } from "../../../models/original/rebate-group.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-rebate-group',
  templateUrl: './view-rebate-group.component.html',
  styleUrls: ['./view-rebate-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRebateGroupComponent implements OnInit {

rebateGroup: RebateGroup=new RebateGroup;

    constructor(private route: ActivatedRoute,private location: Location,private rebateGroupService: RebateGroupService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.rebateGroupService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.rebateGroup = response.data;
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
