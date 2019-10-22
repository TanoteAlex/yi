

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { NationalGroupService } from "../../../services/national-group.service";
import { NationalGroup } from "../../../models/original/national-group.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-national-group',
  templateUrl: './view-national-group.component.html',
  styleUrls: ['./view-national-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewNationalGroupComponent implements OnInit {

nationalGroup: NationalGroup=new NationalGroup;

    constructor(private route: ActivatedRoute,private location: Location,private nationalGroupService: NationalGroupService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.nationalGroupService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.nationalGroup = response.data;
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
