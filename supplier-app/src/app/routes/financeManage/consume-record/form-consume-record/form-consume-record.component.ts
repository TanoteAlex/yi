import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConsumeRecord} from '../../../models/original/consume-record.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-consume-record',
  templateUrl: './form-consume-record.component.html',
  styleUrls: ['./form-consume-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormConsumeRecordComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() consumeRecord: ConsumeRecord =new ConsumeRecord();

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
                msg: '最大64位长度',
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
        tradeNo:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        orderNo:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        consignee:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        payAmount:[
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        finishTime:[
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
        if (value.consumeRecord !== undefined && value.consumeRecord.currentValue !== undefined) {
            this.setBuildFormValue(this.consumeRecord);
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
            tradeNo: [
            ],
            orderNo: [
            ],
            consignee: [
            ],
            payAmount: [
            ],
            finishTime: [
            ],
            remark: [
            ],
        });
    }

    setBuildFormValue(consumeRecord: ConsumeRecord) {
        this.commonForm.setValue({
            guid:
            consumeRecord.guid
                ,
            memberId:
            consumeRecord.memberId
                ,
            tradeNo:
            consumeRecord.tradeNo
                ,
            orderNo:
            consumeRecord.orderNo
                ,
            consignee:
            consumeRecord.consignee
                ,
            payAmount:
            consumeRecord.payAmount
                ,
            finishTime:
            consumeRecord.finishTime
                ,
            remark:
            consumeRecord.remark
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
 if (this.consumeRecord) {
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
