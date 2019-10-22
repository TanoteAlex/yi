
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { IntegralCashBo } from '../../../models/domain/bo/integral-cash-bo.model';
import { IntegralCashService } from '../../../services/integral-cash.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-integral-cash',
    templateUrl: './add-integral-cash.component.html',
    styleUrls: ['./add-integral-cash.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddIntegralCashComponent implements OnInit {

    submitting = false;

    integralCash: IntegralCashBo;

    constructor(private router:Router,private integralCashService: IntegralCashService, public msgSrv: NzMessageService,
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
            this.integralCashService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/integral-cash/list']);
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
