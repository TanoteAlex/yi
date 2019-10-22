import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SaleOrder} from '../../../models/original/sale-order.model';
import {SaleOrderService} from '../../../services/sale-order.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-sale-order',
    templateUrl: './add-sale-order.component.html',
    styleUrls: ['./add-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSaleOrderComponent implements OnInit {

    submitting = false;

    saleOrder: SaleOrder;

    constructor(private router:Router,private saleOrderService: SaleOrderService, public msgSrv: NzMessageService,
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
            this.saleOrderService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/sale-order/list']);
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
