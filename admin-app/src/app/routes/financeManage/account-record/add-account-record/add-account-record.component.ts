
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { AccountRecord } from '../../../models/original/account-record.model';
import { SUCCESS } from '../../../models/constants.model';
import {AccountRecordService} from "../../../services/account-record.service";

@Component({
    selector: 'add-account-record',
    templateUrl: './add-account-record.component.html',
    styleUrls: ['./add-account-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAccountRecordComponent implements OnInit {

    submitting = false;

    accountRecord: AccountRecord;

    constructor(private router:Router,private accountRecordService: AccountRecordService, public msgSrv: NzMessageService,
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
            this.accountRecordService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/account-record/list']);
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
