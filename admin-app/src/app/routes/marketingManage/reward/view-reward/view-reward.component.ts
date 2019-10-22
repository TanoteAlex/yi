

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import { RewardService } from "../../../services/reward.service";
import { RewardVo } from '../../../models/domain/vo/reward-vo.model';
import { Location } from "@angular/common";

@Component({
    selector: 'view-reward',
    templateUrl: './view-reward.component.html',
    styleUrls: ['./view-reward.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewRewardComponent implements OnInit {

    reward: RewardVo = new RewardVo;

    constructor(private route: ActivatedRoute, private location: Location, private rewardService: RewardService,
        public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, ) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.rewardService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.reward = response.data;
            } else {
                this.msg.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msg.error(error.message ? error.message : "请求错误");
        });
    }

    goBack() {
        this.location.back();
    }

}
