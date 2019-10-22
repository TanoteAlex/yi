import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { InviteRewardBo } from '../../../models/domain/bo/invite-reward-bo.model';
import { InviteRewardService } from '../../../services/invite-reward.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
  selector: 'add-invite-reward',
  templateUrl: './add-invite-reward.component.html',
  styleUrls: ['./add-invite-reward.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AddInviteRewardComponent implements OnInit {

  submitting = false;

  inviteReward: InviteRewardBo;

  constructor(private router: Router, private inviteRewardService: InviteRewardService, public msgSrv: NzMessageService,
              private modalService: NzModalService) {
  }

  ngOnInit() {
  }

  onTransmitFormValueSubscription(event) {
    if (event) {
      this.confirmSub(event);
    }
  }

  confirmSub(formValue) {
    if (formValue) {
      this.submitting = true;
      const messageId = this.msgSrv.loading('正在提交...').messageId;
      this.inviteRewardService.add(formValue.obj).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msgSrv.success('操作成功');
          this.router.navigate(['/dashboard/invite-reward/list']);
        } else {
          this.msgSrv.error('请求失败' + response.message);
        }
        this.msgSrv.remove(messageId);
        this.submitting = false;
      }, error => {
        this.msgSrv.error('请求错误' + error.message);
        this.msgSrv.remove(messageId);
        this.submitting = false;
      });
    }
  }

}
