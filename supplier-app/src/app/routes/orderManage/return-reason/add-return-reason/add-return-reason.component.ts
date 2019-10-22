import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ReturnReason} from '../../../models/original/return-reason.model';
import {ReturnReasonService} from '../../../services/return-reason.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-return-reason',
    templateUrl: './add-return-reason.component.html',
    styleUrls: ['./add-return-reason.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddReturnReasonComponent implements OnInit {

    submitting = false;

    returnReason: ReturnReason;

    constructor(private router:Router,private returnReasonService: ReturnReasonService, public msgSrv: NzMessageService,
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
            this.returnReasonService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/return-reason/list']);
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
