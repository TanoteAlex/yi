

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AfterSaleOrderBo } from '../../../models/domain/bo/after-sale-order-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-after-sale-order',
  templateUrl: './form-after-sale-order.component.html',
  styleUrls: ['./form-after-sale-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormAfterSaleOrderComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() afterSaleOrder: AfterSaleOrderBo =new AfterSaleOrderBo();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        member:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        order:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        afterSaleType:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        afterSaleNo:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        applyState:[
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        processState:[
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        refundOrderNo:[
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        refundTradeNo:[
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        refundPayState:[
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        orderAmount:[
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        payAmount:[
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        refundAmount:[
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        actualRefundAmount:[
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        refundMode:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        afterSaleReason:[
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        problemDesc:[
            {
                name: 'maxlength',
                msg: '最大511位长度',
            }
        ],
        voucherPhoto:[
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        contact:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        contactPhone:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        applyTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
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
        if (value.afterSaleOrder !== undefined && value.afterSaleOrder.currentValue !== undefined) {
            this.setBuildFormValue(this.afterSaleOrder);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            member: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            order: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            afterSaleType: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            afterSaleNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            applyState: [
            ],
            processState: [
            ],
            refundOrderNo: [
            ],
            refundTradeNo: [
            ],
            refundPayState: [
            ],
            orderAmount: [
            ],
            payAmount: [
            ],
            refundAmount: [
            ],
            actualRefundAmount: [
            ],
            refundMode: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            afterSaleReason: [
            ],
            problemDesc: [
            ],
            voucherPhoto: [
            ],
            contact: [
            ],
            contactPhone: [
            ],
            applyTime: [
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

    setBuildFormValue(afterSaleOrder: AfterSaleOrderBo) {
        this.commonForm.setValue({
            member:
            afterSaleOrder.member
                ,
            order:
            afterSaleOrder.order
                ,
            afterSaleType:
            afterSaleOrder.afterSaleType
                ,
            afterSaleNo:
            afterSaleOrder.afterSaleNo
                ,
            applyState:
            afterSaleOrder.applyState
                ,
            processState:
            afterSaleOrder.processState
                ,
            refundOrderNo:
            afterSaleOrder.refundOrderNo
                ,
            refundTradeNo:
            afterSaleOrder.refundTradeNo
                ,
            refundPayState:
            afterSaleOrder.refundPayState
                ,
            orderAmount:
            afterSaleOrder.orderAmount
                ,
            payAmount:
            afterSaleOrder.payAmount
                ,
            refundAmount:
            afterSaleOrder.refundAmount
                ,
            actualRefundAmount:
            afterSaleOrder.actualRefundAmount
                ,
            refundMode:
            afterSaleOrder.refundMode
                ,
            afterSaleReason:
            afterSaleOrder.afterSaleReason
                ,
            problemDesc:
            afterSaleOrder.problemDesc
                ,
            voucherPhoto:
            afterSaleOrder.voucherPhoto
                ,
            contact:
            afterSaleOrder.contact
                ,
            contactPhone:
            afterSaleOrder.contactPhone
                ,
            applyTime:
            afterSaleOrder.applyTime
                ,
            remark:
            afterSaleOrder.remark
                ,
            createTime:
            afterSaleOrder.createTime
                ,
            deleted:
            afterSaleOrder.deleted
                ,
            delTime:
            afterSaleOrder.delTime
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
 if (this.afterSaleOrder) {
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
