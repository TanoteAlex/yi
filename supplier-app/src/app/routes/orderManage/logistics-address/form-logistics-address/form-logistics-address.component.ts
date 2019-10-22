import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {LogisticsAddress} from '../../../models/original/logistics-address.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-logistics-address',
  templateUrl: './form-logistics-address.component.html',
  styleUrls: ['./form-logistics-address.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormLogisticsAddressComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() logisticsAddress: LogisticsAddress = new LogisticsAddress();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    addressType: [
      {
        name: 'required',
        msg: '不可为空',
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
    state: [
      {
        name: 'required',
        msg: '不可为空',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.logisticsAddress !== undefined && value.logisticsAddress.currentValue !== undefined) {
      this.setBuildFormValue(this.logisticsAddress);
    }
  }

  ngOnInit() {

  }

  position = [];

  setProvince(event) {
    this.commonForm.patchValue({
      province: event[0],
      city: event[1],
      district: event[2],
    })
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      addressType: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
      contact: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      contactPhone: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      province: [null, Validators.compose([Validators.required, Validators.maxLength(8)])],
      city: [],
      district: [],
      address: [null, Validators.compose([Validators.required, Validators.maxLength(64)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
    });
  }

  setBuildFormValue(logisticsAddress: LogisticsAddress) {
    this.commonForm.setValue({
      addressType: logisticsAddress.addressType,
      contact: logisticsAddress.contact,
      contactPhone: logisticsAddress.contactPhone,
      province: logisticsAddress.province,
      city: logisticsAddress.city,
      district: logisticsAddress.district,
      address: logisticsAddress.address,
      state: logisticsAddress.state,
    });
    if (this.commonForm.value.province != null) {
      this.position[0] = this.commonForm.value.province;
    }
    if (this.commonForm.value.city != null) {
      this.position[1] = this.commonForm.value.city;
    }
    if (this.commonForm.value.district != null) {
      this.position[2] = this.commonForm.value.district;
    }
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
