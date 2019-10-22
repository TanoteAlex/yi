import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Position} from '../../../models/original/position.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-position',
  templateUrl: './form-position.component.html',
  styleUrls: ['./form-position.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormPositionComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() position: Position = new Position();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    name: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    positionType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    remark: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
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

  positionTypes = [{
    code: 1,
    title: "首页轮播图",
  }, {
    code: 2,
    title: "楼层推荐位",
  }, {
    code: 3,
    title: "今日推荐位",
  }, {
    code: 4,
    title: "购物车推荐位",
  }, {
    code: 5,
    title: "邀请有礼推荐位",
  }, {
    code: 6,
    title: "优惠券推荐位",
  }, {
    code: 7,
    title: "双星专区",
  }]

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.position !== undefined && value.position.currentValue !== undefined) {
      this.setBuildFormValue(this.position);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      name: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
      positionType: [null, Validators.compose([Validators.required])],
      remark: [null, Validators.compose([Validators.maxLength(255)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
    });
  }

  setBuildFormValue(position: Position) {
    this.commonForm.setValue({
      name: position.name,
      positionType: position.positionType,
      remark: position.remark,
      state: position.state,
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
    if (this.position) {
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
