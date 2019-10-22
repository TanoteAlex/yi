import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AfterSaleReasonBo} from '../../../models/domain/bo/after-sale-reason-bo.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-after-sale-reason',
  templateUrl: './form-after-sale-reason.component.html',
  styleUrls: ['./form-after-sale-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormAfterSaleReasonComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() afterSaleReason: AfterSaleReasonBo = new AfterSaleReasonBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    afterSaleType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    reason: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    sort: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
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
    if (value.afterSaleReason !== undefined && value.afterSaleReason.currentValue !== undefined) {
      this.setBuildFormValue(this.afterSaleReason);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      afterSaleType: [
        1, Validators.compose([Validators.required,
          Validators.min(1), Validators.max(3)
        ])
      ],
      reason: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(127)
        ])
      ],
      sort: [],
      state: [
        0, Validators.compose([Validators.required,
          Validators.min(0), Validators.max(1)
        ])
      ],
    });
  }

  setBuildFormValue(afterSaleReason: AfterSaleReasonBo) {
    this.commonForm.setValue({
      afterSaleType:
      afterSaleReason.afterSaleType
      ,
      reason:
      afterSaleReason.reason
      ,
      sort:
      afterSaleReason.sort
      ,
      state:
      afterSaleReason.state
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
    if (this.afterSaleReason) {
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
