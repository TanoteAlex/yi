import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { InviteRewardService } from '../../../services/invite-reward.service';
import { InviteRewardBo } from '../../../models/domain/bo/invite-reward-bo.model';

@Component({
  selector: 'edit-invite-reward',
  templateUrl: './edit-invite-reward.component.html',
  styleUrls: ['./edit-invite-reward.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class EditInviteRewardComponent implements OnInit {

  submitting = false;

  inviteReward: InviteRewardBo;

  constructor(private route: ActivatedRoute, private router: Router, private inviteRewardService: InviteRewardService, public msgSrv:
    NzMessageService, private modalService: NzModalService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params['objId']);
    });
  }

  getById(objId) {
    this.inviteRewardService.getBoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.inviteReward = response.data;
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
    }, error => {
      this.msgSrv.error('请求错误', error.message);
    });
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
      formValue.obj.id = this.inviteReward.id;
      this.inviteRewardService.update(formValue.obj).subscribe(response => {
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
