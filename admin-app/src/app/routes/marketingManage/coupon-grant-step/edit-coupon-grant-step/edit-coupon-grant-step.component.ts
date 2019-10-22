
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import { CouponGrantStepService } from '../../../services/coupon-grant-step.service';
import { CouponGrantStepBo } from '../../../models/domain/bo/coupon-grant-step-bo.model';

@Component({
  selector: 'edit-coupon-grant-step',
  templateUrl: './edit-coupon-grant-step.component.html',
  styleUrls: ['./edit-coupon-grant-step.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditCouponGrantStepComponent implements OnInit {

    submitting = false;

    couponGrantStep: CouponGrantStepBo;

    constructor(private route: ActivatedRoute,private router:Router,private couponGrantStepService: CouponGrantStepService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponGrantStepService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.couponGrantStep = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
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
            formValue.obj.id = this.couponGrantStep.id;
            this.couponGrantStepService.update(formValue.obj).subscribe(response => {
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
