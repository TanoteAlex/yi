
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { InviteActivityService } from '../../../services/invite-activity.service';
import { InviteActivityBo } from '../../../models/domain/bo/invite-activity-bo.model';

@Component({
  selector: 'edit-invite-activity',
  templateUrl: './edit-invite-activity.component.html',
  styleUrls: ['./edit-invite-activity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditInviteActivityComponent implements OnInit {

    submitting = false;

    inviteActivity: InviteActivityBo;

    constructor(private route: ActivatedRoute,private router:Router,private inviteActivityService: InviteActivityService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.inviteActivityService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.inviteActivity = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.inviteActivity.id;
            this.inviteActivityService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/invite-activity/list']);
                } else {
                    this.msgSrv.error('请求失败'+response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误'+error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
