import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RefundOrder} from '../../../models/original/refund-order.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-refund-order',
  templateUrl: './form-refund-order.component.html',
  styleUrls: ['./form-refund-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRefundOrderComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() refundOrder: RefundOrder = new RefundOrder();

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
    refundNo: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    applyTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
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
    memberId: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    memberPhone: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    orderId: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    orderNo: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    orderAmount: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    refundAmount: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    refundMode: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大0位长度',
      }
    ],
    refundType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大0位长度',
      }
    ],
    refundReason: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    handleTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    deleted: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大0位长度',
      }
    ],
    delTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    processType: [
      {
        name: 'maxlength',
        msg: '最大0位长度',
      }
    ],
    disposeTime: [
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
    if (value.refundOrder !== undefined && value.refundOrder.currentValue !== undefined) {
      this.setBuildFormValue(this.refundOrder);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      refundNo: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(16)
        ])
      ],
      applyTime: [],
      state: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(0)
        ])
      ],
      memberId: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      memberPhone: [],
      orderId: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      orderNo: [],
      orderAmount: [],
      refundAmount: [],
      refundMode: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(0)
        ])
      ],
      refundType: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(0)
        ])
      ],
      refundReason: [],
      handleTime: [],
      remark: [],
    });
  }

  setBuildFormValue(refundOrder: RefundOrder) {
    this.commonForm.setValue({
      guid:
      refundOrder.guid
      ,
      refundNo:
      refundOrder.refundNo
      ,
      applyTime:
      refundOrder.applyTime
      ,
      state:
      refundOrder.state
      ,
      memberId:
      refundOrder.memberId
      ,
      memberPhone:
      refundOrder.memberPhone
      ,
      orderId:
      refundOrder.orderId
      ,
      orderNo:
      refundOrder.orderNo
      ,
      orderAmount:
      refundOrder.orderAmount
      ,
      refundAmount:
      refundOrder.refundAmount
      ,
      refundMode:
      refundOrder.refundMode
      ,
      refundType:
      refundOrder.refundType
      ,
      refundReason:
      refundOrder.refundReason
      ,
      handleTime:
      refundOrder.handleTime
      ,
      remark:
      refundOrder.remark
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
    if (this.refundOrder) {
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
