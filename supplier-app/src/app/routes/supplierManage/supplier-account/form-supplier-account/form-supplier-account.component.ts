import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SupplierAccount} from '../../../models/original/supplier-account.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-supplier-account',
  templateUrl: './form-supplier-account.component.html',
  styleUrls: ['./form-supplier-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormSupplierAccountComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() supplierAccount: SupplierAccount = new SupplierAccount();

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
    amount: [
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
    freezeAmount: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    withdrawAmount: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    createTime: [
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
    if (value.supplierAccount !== undefined && value.supplierAccount.currentValue !== undefined) {
      this.setBuildFormValue(this.supplierAccount);
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
      amount: [],
      balance: [],
      freezeAmount: [],
      withdrawAmount: [],
      createTime: [],
      remark: [],
    });
  }

  setBuildFormValue(supplierAccount: SupplierAccount) {
    this.commonForm.setValue({
      guid:
      supplierAccount.guid
      ,
      supplier:
      supplierAccount.supplier
      ,
      amount:
      supplierAccount.amount
      ,
      balance:
      supplierAccount.balance
      ,
      freezeAmount:
      supplierAccount.freezeAmount
      ,
      withdrawAmount:
      supplierAccount.withdrawAmount
      ,
      createTime:
      supplierAccount.createTime
      ,
      remark:
      supplierAccount.remark
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
    if (this.supplierAccount) {
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
