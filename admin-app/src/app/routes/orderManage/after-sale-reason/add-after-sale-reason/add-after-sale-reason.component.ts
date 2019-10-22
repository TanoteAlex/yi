
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { AfterSaleReasonBo } from '../../../models/domain/bo/after-sale-reason-bo.model';
import { AfterSaleReasonService } from '../../../services/after-sale-reason.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'add-after-sale-reason',
  templateUrl: './add-after-sale-reason.component.html',
  styleUrls: ['./add-after-sale-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AddAfterSaleReasonComponent implements OnInit {

    submitting = false;

    afterSaleReason: AfterSaleReasonBo;

    constructor(private router:Router,private afterSaleReasonService: AfterSaleReasonService, public msgSrv: NzMessageService,
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
            this.afterSaleReasonService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/after-sale-reason/list']);
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
