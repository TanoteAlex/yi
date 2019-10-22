import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Member} from '../../../models/original/member.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {MemberLevelService} from "../../../services/member-level.service";

@Component({
  selector: 'form-member',
  templateUrl: './form-member.component.html',
  styleUrls: ['./form-member.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormMemberComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() member: Member = new Member();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  position;

  formErrors = {

    parent: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    username: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    nickname: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    memberLevel: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    memberType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    province: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大8位长度',
      }
    ],
    city: [
      {
        name: 'maxlength',
        msg: '最大8位长度',
      }
    ],
    district: [
      {
        name: 'maxlength',
        msg: '最大8位长度',
      }
    ],
    address: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, public memberLevelService: MemberLevelService, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.member !== undefined && value.member.currentValue !== undefined) {
      this.setBuildFormValue(this.member);
    }
  }

  ngOnInit() {

  }

  setMemberLevel(event) {
    this.commonForm.patchValue({
      memberLevel: {
        id: event.id,
        name: event.name
      }
    });
  }

  setProvince(event) {
    this.commonForm.patchValue({
      province: event[0],
      city: event[1],
      district: event[2],
    })
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      memberLevel: [null, Validators.compose([Validators.required])],
      province: [null, Validators.compose([Validators.required])],
      city: [],
      district: [],
      address: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
    });
  }

  setBuildFormValue(member: Member) {
    this.commonForm.setValue({
      memberLevel: {
        id: member.memberLevel.id,
        name: member.memberLevel.name
      },
      province: member.province,
      city: member.city,
      district: member.district,
      address: member.address,
    });
    let position = []
    if (this.commonForm.value.province != null) {
      position.push(this.commonForm.value.province);
    }
    if (this.commonForm.value.city != null) {
      position.push(this.commonForm.value.city);
    }
    if (this.commonForm.value.district != null) {
      position.push(this.commonForm.value.district);
    }
    this.position = position;
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
