import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReturnReason} from '../../../models/original/return-reason.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-return-reason',
  templateUrl: './form-return-reason.component.html',
  styleUrls: ['./form-return-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormReturnReasonComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() returnReason: ReturnReason = new ReturnReason();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    reasonType: [
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
        msg: '最大0位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.returnReason !== undefined && value.returnReason.currentValue !== undefined) {
      this.setBuildFormValue(this.returnReason);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      reasonType: [
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

  setBuildFormValue(returnReason: ReturnReason) {
    this.commonForm.setValue({
      reasonType:
      returnReason.reasonType
      ,
      sort:
      returnReason.sort
      ,
      state:
      returnReason.state
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
    if (this.returnReason) {
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
