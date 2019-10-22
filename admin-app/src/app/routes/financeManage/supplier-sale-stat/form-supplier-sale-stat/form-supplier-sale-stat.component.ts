import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SupplierSaleStat} from '../../../models/original/supplier-sale-stat.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
    selector: 'form-supplier-sale-stat',
    templateUrl: './form-supplier-sale-stat.component.html',
    styleUrls: ['./form-supplier-sale-stat.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormSupplierSaleStatComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() supplierSaleStat: SupplierSaleStat = new SupplierSaleStat();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        orderNum: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        saleAmount: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        platformRatio: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        costAmount: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        profitAmount: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        statTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        endTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.supplierSaleStat !== undefined && value.supplierSaleStat.currentValue !== undefined) {
            this.setBuildFormValue(this.supplierSaleStat);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            orderNum: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            saleAmount: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            platformRatio: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            costAmount: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            profitAmount: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            statTime: [],
            endTime: [],
        });
    }

    setBuildFormValue(supplierSaleStat: SupplierSaleStat) {
        this.commonForm.setValue({
            orderNum:
            supplierSaleStat.orderNum
            ,
            saleAmount:
            supplierSaleStat.saleAmount
            ,
            platformRatio:
            supplierSaleStat.platformRatio
            ,
            costAmount:
            supplierSaleStat.costAmount
            ,
            profitAmount:
            supplierSaleStat.profitAmount
            ,
            statTime:
            supplierSaleStat.statTime
            ,
            endTime:
            supplierSaleStat.endTime
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
        if (this.supplierSaleStat) {
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
