
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { InvitePrizeService } from '../../../services/invite-prize.service';
import { InvitePrizeBo } from '../../../models/domain/bo/invite-prize-bo.model';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-invite-prize',
    templateUrl: './add-invite-prize.component.html',
    styleUrls: ['./add-invite-prize.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddInvitePrizeComponent implements OnInit {

    submitting = false;

    invitePrize: InvitePrizeBo;

    constructor(private router:Router,private invitePrizeService: InvitePrizeService, public msgSrv: NzMessageService,
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
            this.invitePrizeService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/invite-prize/list']);
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
