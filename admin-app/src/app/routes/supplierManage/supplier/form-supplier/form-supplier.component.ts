import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Supplier} from '../../../models/original/supplier.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'form-supplier',
  templateUrl: './form-supplier.component.html',
  styleUrls: ['./form-supplier.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormSupplierComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() supplier: Supplier = new Supplier();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  position;

  formErrors = {


    supplierName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    phone: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    password: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大64位长度',
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
    contact: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    contactPhone: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
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
        msg: '最大64位长度',
      }
    ],
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    settlementCycle: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ]
  };

  constructor(private fb: FormBuilder, public userService: UserService, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.supplier !== undefined && value.supplier.currentValue !== undefined) {
      this.setBuildFormValue(this.supplier);
    }
  }

  ngOnInit() {

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
      supplierName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      phone: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      password: [null, Validators.compose([Validators.required, Validators.maxLength(64)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      contact: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      contactPhone: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      province: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      city: [],
      district: [],
      address: [null, Validators.compose([Validators.required, Validators.maxLength(64)])],
      remark: [null, Validators.compose([Validators.maxLength(127)])],
      settlementCycle: [null, Validators.compose([Validators.maxLength(3)])],
    });
  }

  setBuildFormValue(supplier: Supplier) {

    this.commonForm.setValue({
      supplierName: supplier.supplierName,
      phone: supplier.phone,
      password: supplier.password,
      state: supplier.state,
      contact: supplier.contact,
      contactPhone: supplier.contactPhone,
      settlementCycle: supplier.settlementCycle,
      province: supplier.province,
      city: supplier.city,
      district: supplier.district,
      address: supplier.address,
      remark: supplier.remark,
    });
    let position = [];
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
