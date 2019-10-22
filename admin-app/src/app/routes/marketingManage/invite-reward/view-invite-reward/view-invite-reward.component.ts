import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS } from '../../../models/constants.model';
import { InviteRewardService } from '../../../services/invite-reward.service';
import { InviteRewardVo } from '../../../models/domain/vo/invite-reward-vo.model';
import { Location } from '@angular/common';

@Component({
  selector: 'view-invite-reward',
  templateUrl: './view-invite-reward.component.html',
  styleUrls: ['./view-invite-reward.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ViewInviteRewardComponent implements OnInit {

  inviteReward: InviteRewardVo = new InviteRewardVo;

  constructor(private route: ActivatedRoute, private location: Location, private inviteRewardService: InviteRewardService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params['objId']);
    });
  }

  getById(objId) {
    this.inviteRewardService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.inviteReward = response.data;
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
