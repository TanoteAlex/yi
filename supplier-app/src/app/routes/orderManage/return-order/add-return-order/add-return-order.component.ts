import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ReturnOrder} from '../../../models/original/return-order.model';
import {ReturnOrderService} from '../../../services/return-order.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-return-order',
    templateUrl: './add-return-order.component.html',
    styleUrls: ['./add-return-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddReturnOrderComponent implements OnInit {

    submitting = false;

    returnOrder: ReturnOrder;

    constructor(private router:Router,private returnOrderService: ReturnOrderService, public msgSrv: NzMessageService,
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
            this.returnOrderService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/return-order/list']);
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
