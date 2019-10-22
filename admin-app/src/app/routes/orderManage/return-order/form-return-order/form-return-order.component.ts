import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReturnOrder} from '../../../models/original/return-order.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-return-order',
  templateUrl: './form-return-order.component.html',
  styleUrls: ['./form-return-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormReturnOrderComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() returnOrder: ReturnOrder = new ReturnOrder();

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
    returnNo: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
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
    contact: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    contactPhone: [
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
    freight: [
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
    returnReason: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    problemDescription: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    voucherPhoto: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    consignee: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    consigneePhone: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    consigneeAddr: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
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
    handleTime: [
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
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.returnOrder !== undefined && value.returnOrder.currentValue !== undefined) {
      this.setBuildFormValue(this.returnOrder);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      returnNo: [],
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
      contact: [],
      contactPhone: [],
      orderId: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      orderNo: [],
      orderAmount: [],
      freight: [],
      refundAmount: [],
      returnReason: [],
      problemDescription: [],
      voucherPhoto: [],
      consignee: [],
      consigneePhone: [],
      consigneeAddr: [],
      remark: [],
      handleTime: [],
    });
  }

  setBuildFormValue(returnOrder: ReturnOrder) {
    this.commonForm.setValue({
      id:
      returnOrder.id
      ,
      returnProcess: {
        /*  returnOrderId:
          returnOrder.returnProcesse.id
          ,*/
        user: {
          id: returnOrder.returnProcess.user.id,
          fullName: returnOrder.returnProcess.user.fullName
        }
        ,
        remark:
        returnOrder.returnProcess.remark
        ,
      },
    });

  }

  /*setBuildFormValue(returnOrder: ReturnOrder) {
      this.commonForm.setValue({
          guid:
          returnOrder.guid
              ,
          returnNo:
          returnOrder.returnNo
              ,
          applyTime:
          returnOrder.applyTime
              ,
          state:
          returnOrder.state
              ,
          memberId:
          returnOrder.memberId
              ,
          memberPhone:
          returnOrder.memberPhone
              ,
          contact:
          returnOrder.contact
              ,
          contactPhone:
          returnOrder.contactPhone
              ,
          orderId:
          returnOrder.orderId
              ,
          orderNo:
          returnOrder.orderNo
              ,
          orderAmount:
          returnOrder.orderAmount
              ,
          freight:
          returnOrder.freight
              ,
          refundAmount:
          returnOrder.refundAmount
              ,
          returnReason:
          returnOrder.returnReason
              ,
          problemDescription:
          returnOrder.problemDescription
              ,
          voucherPhoto:
          returnOrder.voucherPhoto
              ,
          consignee:
          returnOrder.consignee
              ,
          consigneePhone:
          returnOrder.consigneePhone
              ,
          consigneeAddr:
          returnOrder.consigneeAddr
              ,
          remark:
          returnOrder.remark
              ,
          handleTime:
          returnOrder.handleTime
              ,
      });
  }*/

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }


  onSubmit() {
    const formValue = this.submitCheck();
    if (this.returnOrder) {
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
