

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS } from '../../../models/constants.model';
import { AreaColumnService } from "../../../services/area-column.service";
import { AreaColumnVo } from '../../../models/domain/vo/area-column-vo.model';
import { Location } from "@angular/common";

@Component({
  selector: 'view-area-column',
  templateUrl: './view-area-column.component.html',
  styleUrls: ['./view-area-column.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAreaColumnComponent implements OnInit {

    areaColumn: AreaColumnVo=new AreaColumnVo;

    constructor(private route: ActivatedRoute,private location: Location,private areaColumnService: AreaColumnService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.areaColumnService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.areaColumn = response.data;
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
