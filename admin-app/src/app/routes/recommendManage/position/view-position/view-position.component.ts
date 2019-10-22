

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { PositionService } from "../../../services/position.service";
import { Position } from "../../../models/original/position.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-position',
  templateUrl: './view-position.component.html',
  styleUrls: ['./view-position.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewPositionComponent implements OnInit {

position: Position=new Position;

    constructor(private route: ActivatedRoute,private location: Location,private positionService: PositionService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.positionService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.position = response.data;
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
