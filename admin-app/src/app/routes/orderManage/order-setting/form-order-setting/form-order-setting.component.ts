import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {OrderSetting} from '../../../models/original/order-setting.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-order-setting',
  templateUrl: './form-order-setting.component.html',
  styleUrls: ['./form-order-setting.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormOrderSettingComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() orderSetting: OrderSetting = new OrderSetting();

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
    setType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    timeout: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    day: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    hour: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    minute: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
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
    if (value.orderSetting !== undefined && value.orderSetting.currentValue !== undefined) {
      this.setBuildFormValue(this.orderSetting);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      setType: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(3)
        ])
      ],
      timeout: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      day: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      hour: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      minute: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      createTime: [],
      deleted: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(3)
        ])
      ],
      delTime: [],
    });
  }

  setBuildFormValue(orderSetting: OrderSetting) {
    this.commonForm.setValue({
      guid:
      orderSetting.guid
      ,
      setType:
      orderSetting.setType
      ,
      timeout:
      orderSetting.timeout
      ,
      day:
      orderSetting.day
      ,
      hour:
      orderSetting.hour
      ,
      minute:
      orderSetting.minute
      ,
      createTime:
      orderSetting.createTime
      ,
      deleted:
      orderSetting.deleted
      ,
      delTime:
      orderSetting.delTime
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
    if (this.orderSetting) {
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
