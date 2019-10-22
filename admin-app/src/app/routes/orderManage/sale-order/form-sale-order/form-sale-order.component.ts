import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SaleOrder} from '../../../models/original/sale-order.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-sale-order',
  templateUrl: './form-sale-order.component.html',
  styleUrls: ['./form-sale-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormSaleOrderComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() saleOrder: SaleOrder = new SaleOrder();

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
    member: [
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
    orderState: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    buyerMessage: [
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
    deliveryMode: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    expressCompany: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    expressNo: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    orderAmount: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    discountAmount: [
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
    payAmount: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    tradeNo: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    payMode: [
      {
        name: 'maxlength',
        msg: '最大0位长度',
      }
    ],
    orderTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    paymentTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    deliveryTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    receiptTime: [
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
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.saleOrder !== undefined && value.saleOrder.currentValue !== undefined) {
      this.setBuildFormValue(this.saleOrder);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      member: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      // supplier: [
      // ],
      orderNo: [],
      orderState: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(3)
        ])
      ],
      buyerMessage: [],
      consignee: [],
      consigneePhone: [],
      consigneeAddr: [],
      deliveryMode: [],
      expressCompany: [],
      expressNo: [],
      orderAmount: [],
      //discountAmount: [],
      freight: [],
      payAmount: [],
      tradeNo: [],
      payMode: [],
      orderTime: [],
      paymentTime: [],
      deliveryTime: [],
      receiptTime: [],
      remark: [],
    });
  }

  setBuildFormValue(saleOrder: SaleOrder) {
    this.commonForm.setValue({
      guid:
      saleOrder.guid
      ,
      member:
      saleOrder.member
      ,
      // supplier:
      // saleOrder.supplier
      //     ,
      orderNo:
      saleOrder.orderNo
      ,
      orderState:
      saleOrder.orderState
      ,
      buyerMessage:
      saleOrder.buyerMessage
      ,
      consignee:
      saleOrder.consignee
      ,
      consigneePhone:
      saleOrder.consigneePhone
      ,
      consigneeAddr:
      saleOrder.consigneeAddr
      ,
      deliveryMode:
      saleOrder.deliveryMode
      ,
      expressCompany:
      saleOrder.expressCompany
      ,
      expressNo:
      saleOrder.expressNo
      ,
      orderAmount:
      saleOrder.orderAmount
      ,
//      discountAmount:
//      saleOrder.discountAmount
//      ,
      freight:
      saleOrder.freight
      ,
      payAmount:
      saleOrder.payAmount
      ,
      tradeNo:
      saleOrder.tradeNo
      ,
      payMode:
      saleOrder.payMode
      ,
      orderTime:
      saleOrder.orderTime
      ,
      paymentTime:
      saleOrder.paymentTime
      ,
      deliveryTime:
      saleOrder.deliveryTime
      ,
      receiptTime:
      saleOrder.receiptTime
      ,
      remark:
      saleOrder.remark
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
    if (this.saleOrder) {
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
