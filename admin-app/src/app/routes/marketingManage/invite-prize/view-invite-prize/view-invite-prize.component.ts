

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { InvitePrizeService } from "../../../services/invite-prize.service";
import { InvitePrizeVo } from '../../../models/domain/vo/invite-prize-vo.model';
import { Location } from "@angular/common";
import { SUCCESS } from '../../../models/constants.model';

@Component({
  selector: 'view-invite-prize',
  templateUrl: './view-invite-prize.component.html',
  styleUrls: ['./view-invite-prize.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewInvitePrizeComponent implements OnInit {

    invitePrize: InvitePrizeVo=new InvitePrizeVo;

    constructor(private route: ActivatedRoute,private location: Location,private invitePrizeService: InvitePrizeService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.invitePrizeService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.invitePrize = response.data;
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
