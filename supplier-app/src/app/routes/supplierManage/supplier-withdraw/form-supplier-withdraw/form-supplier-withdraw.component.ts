import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SupplierWithdraw} from '../../../models/original/supplier-withdraw.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
    selector: 'form-supplier-withdraw',
    templateUrl: './form-supplier-withdraw.component.html',
    styleUrls: ['./form-supplier-withdraw.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormSupplierWithdrawComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() supplierWithdraw: SupplierWithdraw = new SupplierWithdraw();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {
        payee: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        receiptsNo: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        receiptsName: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        applyAmount: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        paymentMethod: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.supplierWithdraw !== undefined && value.supplierWithdraw.currentValue !== undefined) {
            this.setBuildFormValue(this.supplierWithdraw);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            supplier: [
            ],
            supplierName: [
            ],
            payee: [
            ],
            receiptsNo: [
            ],
            receiptsName: [
            ],
            applyAmount: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            paymentMethod: [
                1, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            state: [
                1, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
        });
    }

    setBuildFormValue(supplierWithdraw: SupplierWithdraw) {
        this.commonForm.setValue({
            supplier: supplierWithdraw.supplier,
            supplierName: supplierWithdraw.supplierName,
            payee: supplierWithdraw.payee,
            receiptsNo: supplierWithdraw.receiptsNo,
            receiptsName: supplierWithdraw.receiptsName,
            applyAmount: supplierWithdraw.applyAmount,
            paymentMethod: supplierWithdraw.paymentMethod,
            state: supplierWithdraw.state,
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
        if (this.supplierWithdraw) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
