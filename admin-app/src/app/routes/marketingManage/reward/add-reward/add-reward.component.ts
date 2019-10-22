
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { RewardBo } from '../../../models/domain/bo/reward-bo.model';
import { RewardService } from '../../../services/reward.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-reward',
    templateUrl: './add-reward.component.html',
    styleUrls: ['./add-reward.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRewardComponent implements OnInit {

    submitting = false;

    reward: RewardBo;

    constructor(private router: Router, private rewardService: RewardService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue) {
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.rewardService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/reward/list']);
                } else {
                    this.msgSrv.error(response.message ? response.message : "请求失败");
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error(error.message ? error.message : "请求错误");
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }

}
