import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IntegralCashBo } from '../../../models/domain/bo/integral-cash-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-integral-cash',
  templateUrl: './form-integral-cash.component.html',
  styleUrls: ['./form-integral-cash.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FormIntegralCashComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() integralCash: IntegralCashBo = new IntegralCashBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {
    integral: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      },
    ],
    cash: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大9位长度',
      },
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.integralCash !== undefined && value.integralCash.currentValue !== undefined) {
      this.setBuildFormValue(this.integralCash);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      integral: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ]),
      ],
      cash: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(9),
        ]),
      ],
      state: [
        0, Validators.compose([Validators.required,
        ]),
      ],
    });
  }

  setBuildFormValue(integralCash: IntegralCashBo) {
    this.commonForm.setValue({
      integral:
      integralCash.integral
      ,
      cash:
      integralCash.cash
      ,
      state:
      integralCash.state,
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
    if (this.integralCash) {
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
