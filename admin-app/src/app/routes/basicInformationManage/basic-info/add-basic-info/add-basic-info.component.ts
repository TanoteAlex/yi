
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { BasicInfo } from '../../../models/original/basic-info.model';
import { BasicInfoService } from '../../../services/basic-info.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-basic-info',
    templateUrl: './add-basic-info.component.html',
    styleUrls: ['./add-basic-info.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddBasicInfoComponent implements OnInit {

    submitting = false;

    basicInfo: BasicInfo;

    constructor(private router:Router,private basicInfoService: BasicInfoService, public msgSrv: NzMessageService,
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
            this.basicInfoService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/basic-info/list']);
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
