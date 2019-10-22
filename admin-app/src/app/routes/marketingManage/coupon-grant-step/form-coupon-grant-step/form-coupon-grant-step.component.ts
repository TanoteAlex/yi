

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CouponGrantStepBo } from '../../../models/domain/bo/coupon-grant-step-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-coupon-grant-step',
  templateUrl: './form-coupon-grant-step.component.html',
  styleUrls: ['./form-coupon-grant-step.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormCouponGrantStepComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() couponGrantStep: CouponGrantStepBo =new CouponGrantStepBo();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        couponGrantConfig:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        grantNode:[
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        grantRate:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大5位长度',
            }
        ],
        remark:[
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.couponGrantStep !== undefined && value.couponGrantStep.currentValue !== undefined) {
            this.setBuildFormValue(this.couponGrantStep);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            couponGrantConfig: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            grantNode: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(4)])],
            grantRate: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(5)
                ])
            ],
            remark: [
            ],
        });
    }

    setBuildFormValue(couponGrantStep: CouponGrantStepBo) {
        this.commonForm.setValue({
            couponGrantConfig:
            couponGrantStep.couponGrantConfig
                ,
            grantNode:
            couponGrantStep.grantNode
                ,
            grantRate:
            couponGrantStep.grantRate
                ,
            remark:
            couponGrantStep.remark
                ,
        });
    }

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
        if (commonFormValid) {
            return this.commonForm.value;
        }
        return null;
    }


    onSubmit() {
        const formValue = this.submitCheck();
 if (this.couponGrantStep) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack(){
        this.location.back();
    }

}
