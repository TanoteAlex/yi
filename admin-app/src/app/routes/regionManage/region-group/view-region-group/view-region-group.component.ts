

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { RegionGroupService } from "../../../services/region-group.service";
import { RegionGroup } from "../../../models/original/region-group.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-region-group',
  templateUrl: './view-region-group.component.html',
  styleUrls: ['./view-region-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRegionGroupComponent implements OnInit {

regionGroup: RegionGroup=new RegionGroup;

    constructor(private route: ActivatedRoute,private location: Location,private regionGroupService: RegionGroupService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.regionGroupService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.regionGroup = response.data;
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
