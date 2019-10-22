import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {IntegralRecord} from '../../../models/original/integral-record.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-integral-record',
  templateUrl: './form-integral-record.component.html',
  styleUrls: ['./form-integral-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormIntegralRecordComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() integralRecord: IntegralRecord = new IntegralRecord();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    member: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    integralTask: [
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
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    operateIntegral: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    currentIntegral: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.integralRecord !== undefined && value.integralRecord.currentValue !== undefined) {
      this.setBuildFormValue(this.integralRecord);
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
      integralTask: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      operateType: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(3)
        ])
      ],
      operateIntegral: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      currentIntegral: [],
    });
  }

  setBuildFormValue(integralRecord: IntegralRecord) {
    this.commonForm.setValue({
      member:
      integralRecord.member
      ,
      integralTask:
      integralRecord.integralTask
      ,
      operateType:
      integralRecord.operateType
      ,
      operateIntegral:
      integralRecord.operateIntegral
      ,
      currentIntegral:
      integralRecord.currentIntegral
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
    if (this.integralRecord) {
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
