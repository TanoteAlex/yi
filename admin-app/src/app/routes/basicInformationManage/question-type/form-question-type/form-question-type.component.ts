import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {QuestionType} from '../../../models/original/question-type.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-question-type',
  templateUrl: './form-question-type.component.html',
  styleUrls: ['./form-question-type.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormQuestionTypeComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() questionType: QuestionType = new QuestionType();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {
    typeName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    sort: [],
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
    if (value.questionType !== undefined && value.questionType.currentValue !== undefined) {
      this.setBuildFormValue(this.questionType);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      typeName: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
      sort: [],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
    });
  }

  setBuildFormValue(questionType: QuestionType) {
    this.commonForm.setValue({
      typeName: questionType.typeName,
      sort: questionType.sort,
      state: questionType.state,
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
