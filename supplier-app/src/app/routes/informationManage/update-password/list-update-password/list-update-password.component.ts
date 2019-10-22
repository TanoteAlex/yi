import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierService} from '../../../services/supplier.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {Supplier} from "../../../models/original/supplier.model";
import {ObjectUtils} from "@shared/utils/ObjectUtils";
import {Location} from "@angular/common";

@Component({
  selector: 'list-login-password',
  templateUrl: './list-update-password.component.html',
  styleUrls: ['./list-update-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListUpdatePasswordComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  commonForm: FormGroup;

  @Input() supplier: Supplier = new Supplier();

  formErrors = {

    password: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    newPassword: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'minLength',
        msg: '最大6位长度',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    sureNewPassword: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'minLength',
        msg: '最大6位长度',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
  };

  constructor(public route: ActivatedRoute, public router: Router, private supplierService: SupplierService, public msg: NzMessageService, private location: Location,
              private fb: FormBuilder) {
    this.buildForm()
  }

  ngOnInit() {
    this.getById();
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      password: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      newPassword: [null, Validators.compose([Validators.required, Validators.minLength(6),Validators.maxLength(16)])],
      sureNewPassword: [null, Validators.compose([Validators.required, Validators.minLength(6),Validators.maxLength(16)])],
    });

  }

  getById() {
    this.supplierService.getForSupplier().subscribe(response => {
      if (response.result == SUCCESS) {
        this.supplier = response.data;
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
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
    const searchObj = this.submitCheck();
    if (!searchObj) {
      this.msg.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    if(searchObj.password != this.supplier.password){
      this.msg.warning('原登录密码错误！');
      return;
    }
    if(searchObj.newPassword != searchObj.sureNewPassword){
      this.msg.warning('两次密码不一致！');
      return;
    }
    searchObj.id=this.supplier.id;
    searchObj.password=searchObj.newPassword;
    searchObj.phone=this.supplier.phone;
    searchObj.state=this.supplier.state;
    searchObj.supplierName=this.supplier.supplierName;
    searchObj.contact=this.supplier.contact;
    searchObj.contactPhone=this.supplier.contactPhone;
    searchObj.province=this.supplier.province;
    searchObj.city=this.supplier.city;
    searchObj.district=this.supplier.district;
    searchObj.address=this.supplier.address;
    this.supplierService.updateForSupplier(searchObj).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("操作成功");
        this.router.navigate(['/dashboard/update-password/list']);
      } else {
        this.msg.error('请求失败'+response.message);
      }
    }, error => {
      this.msg.error('请求错误'+error.message);
    });
  }

  goBack() {
    this.location.back();
  }

}
