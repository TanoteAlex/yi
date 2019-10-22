
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import {GroupBuyOrderBo} from '../../../models/domain/bo/group-buy-order-bo.model';
import {GroupBuyOrderService} from '../../../services/group-buy-order.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-group-buy-order',
    templateUrl: './add-group-buy-order.component.html',
    styleUrls: ['./add-group-buy-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddGroupBuyOrderComponent implements OnInit {

    submitting = false;

    groupBuyOrder: GroupBuyOrderBo;

    constructor(private router:Router,private groupBuyOrderService: GroupBuyOrderService, public msgSrv: NzMessageService,
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
            this.groupBuyOrderService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/group-buy-order/list']);
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
