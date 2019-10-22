

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CouponGrantRecordBo } from '../../../models/domain/bo/coupon-grant-record-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-voucher-grant-record',
  templateUrl: './form-voucher-grant-record.component.html',
  styleUrls: ['./form-voucher-grant-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormVoucherGrantRecordComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() couponGrantRecord: CouponGrantRecordBo =new CouponGrantRecordBo();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        id:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        guid:[
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        memberId:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        couponGrantConfigId:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        couponId:[
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
        parValue:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        remark:[
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        createTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        deleted:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        delTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.couponGrantRecord !== undefined && value.couponGrantRecord.currentValue !== undefined) {
            this.setBuildFormValue(this.couponGrantRecord);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
            ],
            memberId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            couponGrantConfigId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            couponId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            grantNode: [
            ],
            parValue: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            remark: [
            ],
            createTime: [
            ],
            deleted: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            delTime: [
            ],
        });
    }

    setBuildFormValue(couponGrantRecord: CouponGrantRecordBo) {
        this.commonForm.setValue({
            guid:
            couponGrantRecord.guid
                ,
            memberId:
            couponGrantRecord.memberId
                ,
            couponGrantConfigId:
            couponGrantRecord.couponGrantConfigId
                ,
            couponId:
            couponGrantRecord.couponId
                ,
            grantNode:
            couponGrantRecord.grantNode
                ,
            parValue:
            couponGrantRecord.parValue
                ,
            remark:
            couponGrantRecord.remark
                ,
            createTime:
            couponGrantRecord.createTime
                ,
            deleted:
            couponGrantRecord.deleted
                ,
            delTime:
            couponGrantRecord.delTime
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
 if (this.couponGrantRecord) {
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
