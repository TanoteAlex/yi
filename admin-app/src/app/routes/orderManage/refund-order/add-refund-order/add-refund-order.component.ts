import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {RefundOrder} from '../../../models/original/refund-order.model';
import {RefundOrderService} from '../../../services/refund-order.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-refund-order',
    templateUrl: './add-refund-order.component.html',
    styleUrls: ['./add-refund-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRefundOrderComponent implements OnInit {

    submitting = false;

    refundOrder: RefundOrder;

    constructor(private router:Router,private refundOrderService: RefundOrderService, public msgSrv: NzMessageService,
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
            this.refundOrderService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/refund-order/list']);
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
