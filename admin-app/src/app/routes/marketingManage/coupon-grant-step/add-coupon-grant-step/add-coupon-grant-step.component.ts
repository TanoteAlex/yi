
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { CouponGrantStepBo } from '../../../models/domain/bo/coupon-grant-step-bo.model';
import { CouponGrantStepService } from '../../../services/coupon-grant-step.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-coupon-grant-step',
    templateUrl: './add-coupon-grant-step.component.html',
    styleUrls: ['./add-coupon-grant-step.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddCouponGrantStepComponent implements OnInit {

    submitting = false;

    couponGrantStep: CouponGrantStepBo;

    constructor(private router:Router,private couponGrantStepService: CouponGrantStepService, public msgSrv: NzMessageService,
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
            this.couponGrantStepService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/coupon-grant-step/list']);
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
