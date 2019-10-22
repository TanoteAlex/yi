
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { AfterSaleOrderBo } from '../../../models/domain/bo/after-sale-order-bo.model';
import { AfterSaleOrderService } from '../../../services/after-sale-order.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-after-sale-order',
    templateUrl: './add-after-sale-order.component.html',
    styleUrls: ['./add-after-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAfterSaleOrderComponent implements OnInit {

    submitting = false;

    afterSaleOrder: AfterSaleOrderBo;

    constructor(private router:Router,private afterSaleOrderService: AfterSaleOrderService, public msgSrv: NzMessageService,
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
            this.afterSaleOrderService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/after-sale-order/list']);
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
