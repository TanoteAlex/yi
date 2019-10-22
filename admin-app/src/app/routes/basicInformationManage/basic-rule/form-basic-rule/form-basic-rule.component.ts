import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BasicRule} from '../../../models/original/basic-rule.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-basic-rule',
  templateUrl: './form-basic-rule.component.html',
  styleUrls: ['./form-basic-rule.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormBasicRuleComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() basicRule: BasicRule = new BasicRule();

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
    title: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    content: [
      {
        name: 'maxlength',
        msg: '最大65535位长度',
      }
    ],
    createTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    deleted: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    delTime: [
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
    if (value.basicRule !== undefined && value.basicRule.currentValue !== undefined) {
      this.setBuildFormValue(this.basicRule);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      title: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(127)
        ])
      ],
      content: [],
      createTime: [],
      deleted: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(3)
        ])
      ],
      delTime: [],
    });
  }

  setBuildFormValue(basicRule: BasicRule) {
    this.commonForm.setValue({
      guid:
      basicRule.guid
      ,
      title:
      basicRule.title
      ,
      content:
      basicRule.content
      ,
      createTime:
      basicRule.createTime
      ,
      deleted:
      basicRule.deleted
      ,
      delTime:
      basicRule.delTime
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
    if (this.basicRule) {
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
