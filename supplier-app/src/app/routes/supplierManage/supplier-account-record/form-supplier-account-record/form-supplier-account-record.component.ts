import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {SupplierAccountRecord} from "../../../models/original/supplier-account-record.model";

@Component({
  selector: 'form-supplier-account-record',
  templateUrl: './form-supplier-account-record.component.html',
  styleUrls: ['./form-supplier-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormSupplierAccountRecordComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() supplierAccountRecord: SupplierAccountRecord = new SupplierAccountRecord();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    id: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    guid: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    supplier: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    operateType: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    serialNo: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    tradeAmount: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    balance: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    tradeType: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    tradeTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.supplierAccountRecord !== undefined && value.supplierAccountRecord.currentValue !== undefined) {
      this.setBuildFormValue(this.supplierAccountRecord);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      supplier: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      operateType: [],
      serialNo: [],
      tradeAmount: [],
      balance: [],
      tradeType: [],
      tradeTime: [],
      remark: [],
    });
  }

  setBuildFormValue(supplierAccountRecord: SupplierAccountRecord) {
    this.commonForm.setValue({
      guid:
      supplierAccountRecord.guid
      ,
      supplier:
      supplierAccountRecord.supplier
      ,
      operateType:
      supplierAccountRecord.operateType
      ,
      serialNo:
      supplierAccountRecord.serialNo
      ,
      tradeAmount:
      supplierAccountRecord.tradeAmount
      ,
      balance:
      supplierAccountRecord.balance
      ,
      tradeType:
      supplierAccountRecord.tradeType
      ,
      tradeTime:
      supplierAccountRecord.tradeTime
      ,
      remark:
      supplierAccountRecord.remark
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
    if (this.supplierAccountRecord) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

}
