

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS } from '../../../models/constants.model';
import { InviteActivityService } from "../../../services/invite-activity.service";
import { InviteActivityVo } from '../../../models/domain/vo/invite-activity-vo.model';
import { Location } from "@angular/common";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
    selector: 'view-invite-activity',
    templateUrl: './view-invite-activity.component.html',
    styleUrls: ['./view-invite-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewInviteActivityComponent implements OnInit {

    inviteActivity: InviteActivityVo = new InviteActivityVo;

    constructor(public domSanitizer: DomSanitizer, private route: ActivatedRoute, private location: Location, private inviteActivityService: InviteActivityService,
        public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, public sanli: DomSanitizer) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.inviteActivityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.inviteActivity = response.data;
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
