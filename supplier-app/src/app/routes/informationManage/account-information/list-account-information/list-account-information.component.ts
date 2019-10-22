import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierService} from '../../../services/supplier.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {Supplier} from "../../../models/original/supplier.model";
import {Location} from "@angular/common";
import {ObjectUtils} from "@shared/utils/ObjectUtils";

@Component({
  selector: 'list-account-information',
  templateUrl: './list-account-information.component.html',
  styleUrls: ['./list-account-information.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListAccountInformationComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  commonForm: FormGroup;

  @Input() supplier: Supplier = new Supplier();

  position = [];

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
  };

  constructor(public route: ActivatedRoute, public router: Router, private supplierService: SupplierService, public msg: NzMessageService, private location: Location,
              private fb: FormBuilder) {
    this.buildForm()
  }

  ngOnChanges(value) {
    if (value.supplier !== undefined && value.supplier.currentValue !== undefined) {
      this.setBuildFormValue(this.supplier);
    }
  }

  ngOnInit() {
    this.getById()
  }


  getById() {
    this.supplierService.getForSupplier().subscribe(response => {
      if (response.result == SUCCESS) {
        this.supplier = response.data;
        this.setBuildFormValue(this.supplier);
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      supplierName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      contact: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      contactPhone: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      province: [null, Validators.compose([Validators.required, Validators.maxLength(8)])],
      city: [],
      district: [],
      address: [null, Validators.compose([Validators.required, Validators.maxLength(64)])],
    });
  }

  setProvince(event) {
    this.commonForm.patchValue({
      province: event[0],
      city: event[1],
      district: event[2],
    })
  }

  setBuildFormValue(supplier: Supplier) {
    this.commonForm.setValue({
      supplierName: supplier.supplierName,
      contact: supplier.contact,
      contactPhone: supplier.contactPhone,
      province: supplier.province,
      city: supplier.city,
      district: supplier.district,
      address: supplier.address,
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
      this.msg.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    const searchObj = this.commonForm.value;
    searchObj.id = this.supplier.id;
    searchObj.password = this.supplier.password;
    searchObj.phone = this.supplier.phone;
    searchObj.state = this.supplier.state;
    this.supplierService.updateForSupplier(searchObj).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("操作成功");
        this.router.navigate(['/dashboard/account-information/list']);
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }

  goBack() {
    this.location.back();
  }

}
