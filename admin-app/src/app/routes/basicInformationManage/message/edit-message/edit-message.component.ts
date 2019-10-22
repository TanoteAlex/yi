import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { MessageService } from '../../../services/message.service';
import { Message } from '../../../models/original/message.model';

@Component({
    selector: 'edit-message',
    templateUrl: './edit-message.component.html',
    styleUrls: ['./edit-message.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditMessageComponent implements OnInit {

    submitting = false;

    message: Message;

    constructor(private route: ActivatedRoute, private router: Router, private messageService: MessageService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.messageService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.message = response.data;
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
            formValue.obj.id = this.message.id;
            this.messageService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/message/list']);
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
