
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { OrderLog } from '../../../models/original/order-log.model';
import { OrderLogService } from '../../../services/order-log.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-order-log',
    templateUrl: './add-order-log.component.html',
    styleUrls: ['./add-order-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddOrderLogComponent implements OnInit {

    submitting = false;

    orderLog: OrderLog;

    constructor(private router:Router,private orderLogService: OrderLogService, public msgSrv: NzMessageService,
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
            this.orderLogService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/order-log/list']);
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
