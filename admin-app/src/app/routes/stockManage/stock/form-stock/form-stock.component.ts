import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Stock} from '../../../models/original/stock.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-stock',
  templateUrl: './form-stock.component.html',
  styleUrls: ['./form-stock.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormStockComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() stock: Stock = new Stock();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    commodity: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    product: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    stockQuantity: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    lockQuantity: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    useQuantity: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    sort: [],
    remark: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    commodityName:[],
    productName:[],
    supplierName:[],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.stock !== undefined && value.stock.currentValue !== undefined) {
      this.setBuildFormValue(this.stock);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      commodity: [],
      product: [],
      stockQuantity: [null, Validators.compose([Validators.required])],
      lockQuantity: [null, Validators.compose([Validators.required])],
      useQuantity: [null, Validators.compose([Validators.required])],
      sort: [],
      remark: [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      commodityName:[null],
      productName:[null],
      supplierName:[null],
    });
  }


  setBuildFormValue(stock: Stock) {
    this.commonForm.setValue({
      commodity: stock.commodity,
      product: stock.product,
      stockQuantity: stock.stockQuantity,
      lockQuantity: stock.lockQuantity,
      useQuantity: stock.useQuantity,
      sort: stock.sort,
      remark: stock.remark,
      commodityName: stock.commodity.commodityName,
      productName: stock.product.productName,
      supplierName: stock.supplier.supplierName,
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
