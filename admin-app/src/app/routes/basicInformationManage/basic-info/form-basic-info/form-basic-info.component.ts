import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BasicInfo} from '../../../models/original/basic-info.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-basic-info',
  templateUrl: './form-basic-info.component.html',
  styleUrls: ['./form-basic-info.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormBasicInfoComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() basicInfo: BasicInfo = new BasicInfo();

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
    logoUrl: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    companyName: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    companyAddr: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    companyTel: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    companyMobile: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    companyMail: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    setupTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    contentProfile: [
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
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.basicInfo !== undefined && value.basicInfo.currentValue !== undefined) {
      this.setBuildFormValue(this.basicInfo);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      logoUrl: [],
      companyName: [],
      companyAddr: [],
      companyTel: [],
      companyMobile: [],
      companyMail: [],
      setupTime: [],
      contentProfile: [],
      createTime: [],
    });
  }

  setBuildFormValue(basicInfo: BasicInfo) {
    this.commonForm.setValue({
      guid:
      basicInfo.guid
      ,
      logoUrl:
      basicInfo.logoUrl
      ,
      companyName:
      basicInfo.companyName
      ,
      companyAddr:
      basicInfo.companyAddr
      ,
      companyTel:
      basicInfo.companyTel
      ,
      companyMobile:
      basicInfo.companyMobile
      ,
      companyMail:
      basicInfo.companyMail
      ,
      setupTime:
      basicInfo.setupTime
      ,
      contentProfile:
      basicInfo.contentProfile
      ,
      createTime:
      basicInfo.createTime
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
    if (this.basicInfo) {
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
