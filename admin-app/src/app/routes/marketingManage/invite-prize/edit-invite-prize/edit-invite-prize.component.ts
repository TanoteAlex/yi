
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { InvitePrizeService } from '../../../services/invite-prize.service';
import { InvitePrizeBo } from '../../../models/domain/bo/invite-prize-bo.model';

@Component({
  selector: 'edit-invite-prize',
  templateUrl: './edit-invite-prize.component.html',
  styleUrls: ['./edit-invite-prize.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditInvitePrizeComponent implements OnInit {

    submitting = false;

    invitePrize: InvitePrizeBo;

    constructor(private route: ActivatedRoute,private router:Router,private invitePrizeService: InvitePrizeService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.invitePrizeService.getById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.invitePrize = response.data;
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
            formValue.obj.id = this.invitePrize.id;
            this.invitePrizeService.update(formValue.obj).subscribe(response => {
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
