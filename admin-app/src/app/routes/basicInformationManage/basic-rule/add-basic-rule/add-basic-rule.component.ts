
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { BasicRule } from '../../../models/original/basic-rule.model';
import { BasicRuleService } from '../../../services/basic-rule.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-basic-rule',
    templateUrl: './add-basic-rule.component.html',
    styleUrls: ['./add-basic-rule.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddBasicRuleComponent implements OnInit {

    submitting = false;

    basicRule: BasicRule;

    constructor(private router:Router,private basicRuleService: BasicRuleService, public msgSrv: NzMessageService,
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
            this.basicRuleService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/basic-rule/list']);
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
