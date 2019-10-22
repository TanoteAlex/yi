

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { BasicInfoService } from "../../../services/basic-info.service";
import { BasicInfo } from "../../../models/original/basic-info.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-basic-info',
  templateUrl: './view-basic-info.component.html',
  styleUrls: ['./view-basic-info.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewBasicInfoComponent implements OnInit {

basicInfo: BasicInfo=new BasicInfo;

    constructor(private route: ActivatedRoute,private location: Location,private basicInfoService: BasicInfoService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.basicInfoService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.basicInfo = response.data;
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
