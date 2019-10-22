
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { CouponGrantRecordBo } from '../../../models/domain/bo/coupon-grant-record-bo.model';
import { CouponGrantRecordService } from '../../../services/coupon-grant-record.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-voucher-grant-record',
    templateUrl: './add-voucher-grant-record.component.html',
    styleUrls: ['./add-voucher-grant-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddVoucherGrantRecordComponent implements OnInit {

    submitting = false;

    couponGrantRecord: CouponGrantRecordBo;

    constructor(private router:Router,private couponGrantRecordService: CouponGrantRecordService, public msgSrv: NzMessageService,
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
            this.couponGrantRecordService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/coupon-grant-record/list']);
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
