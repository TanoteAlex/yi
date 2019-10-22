
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { IntegralRecord } from '../../../models/original/integral-record.model';
import { IntegralRecordService } from '../../../services/integral-record.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-integral-record',
    templateUrl: './add-integral-record.component.html',
    styleUrls: ['./add-integral-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddIntegralRecordComponent implements OnInit {

    submitting = false;

    integralRecord: IntegralRecord;

    constructor(private router:Router,private integralRecordService: IntegralRecordService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

    submitForm(formValue) {
        this.modalService.confirm({
            nzTitle  : '<i>提示！</i>',
            nzContent: '<b>确认要提交吗？</b>',
            nzOnOk   : () => this.confirmSub(formValue)
        });
    }

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.integralRecordService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/integral-record/list']);
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
