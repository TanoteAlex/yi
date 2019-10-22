
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { InviteActivityBo } from '../../../models/domain/bo/invite-activity-bo.model';
import { InviteActivityService } from '../../../services/invite-activity.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-invite-activity',
    templateUrl: './add-invite-activity.component.html',
    styleUrls: ['./add-invite-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddInviteActivityComponent implements OnInit {

    submitting = false;

    inviteActivity: InviteActivityBo;

    constructor(private router:Router,private inviteActivityService: InviteActivityService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
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
            this.inviteActivityService.add(formValue.obj).subscribe(response => {
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
