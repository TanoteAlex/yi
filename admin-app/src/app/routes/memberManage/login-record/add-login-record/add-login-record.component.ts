
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { LoginRecordBo } from '../../../models/domain/bo/login-record-bo.model';
import { LoginRecordService } from '../../../services/login-record.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-login-record',
    templateUrl: './add-login-record.component.html',
    styleUrls: ['./add-login-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddLoginRecordComponent implements OnInit {

    submitting = false;

    loginRecord: LoginRecordBo;

    constructor(private router:Router,private loginRecordService: LoginRecordService, public msgSrv: NzMessageService,
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
            this.loginRecordService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/login-record/list']);
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
