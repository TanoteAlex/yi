
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import {SUCCESS} from "../../../models/constants.model";
import { RewardService } from '../../../services/reward.service';
import { RewardBo } from '../../../models/domain/bo/reward-bo.model';

@Component({
    selector: 'edit-reward',
    templateUrl: './edit-reward.component.html',
    styleUrls: ['./edit-reward.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditRewardComponent implements OnInit {

    submitting = false;

    reward: RewardBo;

    constructor(private route: ActivatedRoute, private router: Router, private rewardService: RewardService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

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
                this.msgSrv.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msgSrv.error(error.message ? error.message : "请求错误");
        });
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
            formValue.obj.id = this.reward.id;
            this.rewardService.update(formValue.obj).subscribe(response => {
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
