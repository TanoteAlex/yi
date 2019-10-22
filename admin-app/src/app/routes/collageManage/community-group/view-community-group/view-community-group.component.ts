

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { CommunityGroupService } from "../../../services/community-group.service";
import { CommunityGroup } from "../../../models/original/community-group.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-community-group',
  templateUrl: './view-community-group.component.html',
  styleUrls: ['./view-community-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCommunityGroupComponent implements OnInit {

communityGroup: CommunityGroup=new CommunityGroup;

    constructor(private route: ActivatedRoute,private location: Location,private communityGroupService: CommunityGroupService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.communityGroupService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.communityGroup = response.data;
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
