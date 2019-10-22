

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountRecord} from '../../../models/original/account-record.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-account-record',
  templateUrl: './form-account-record.component.html',
  styleUrls: ['./form-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormAccountRecordComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() accountRecord: AccountRecord =new AccountRecord();

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
        operateType:[
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        serialNo:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        tradeAmount:[
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        balance:[
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        tradeType:[
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        tradeMode:[
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        tradeTime:[
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
    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.accountRecord !== undefined && value.accountRecord.currentValue !== undefined) {
            this.setBuildFormValue(this.accountRecord);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
            ],
            member: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            operateType: [
            ],
            serialNo: [
            ],
            tradeAmount: [
            ],
            balance: [
            ],
            tradeType: [
            ],
            tradeMode: [
            ],
            tradeTime: [
            ],
            remark: [
            ],
        });
    }

    setBuildFormValue(accountRecord: AccountRecord) {
        this.commonForm.setValue({
            guid:
            accountRecord.guid
                ,
            member:
            accountRecord.member
                ,
            operateType:
            accountRecord.operateType
                ,
            serialNo:
            accountRecord.serialNo
                ,
            tradeAmount:
            accountRecord.tradeAmount
                ,
            balance:
            accountRecord.balance
                ,
            tradeType:
            accountRecord.tradeType
                ,
            tradeMode:
            accountRecord.tradeMode
                ,
            tradeTime:
            accountRecord.tradeTime
                ,
            remark:
            accountRecord.remark
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
 if (this.accountRecord) {
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
