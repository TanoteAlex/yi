import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {User} from '../../../models/original/user.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {DeptService} from "../../../services/dept.service";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";
import {RoleService} from "../../../services/role.service";

@Component({
  selector: 'form-user',
  templateUrl: './form-user.component.html',
  styleUrls: ['./form-user.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormUserComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() user: User = new User();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('deptModalSelect') deptModalSelect: ModalSelecetComponent;
  @ViewChild('roleSelect') roleSelect: ModalSelecetComponent;

  formErrors = {

    dept: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    username: [
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
        msg: '最大32位长度',
      }
    ],
    fullName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
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
    email: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    jobNo: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    avatar: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
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
    roles: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public deptService: DeptService,public roleService: RoleService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.user !== undefined && value.user.currentValue !== undefined) {
      this.setBuildFormValue(this.user);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      dept: [null, Validators.compose([Validators.required])],
      username: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      password: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      fullName: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      phone: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      email: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      jobNo: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      avatar: [],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      roles: [null, Validators.compose([Validators.required])],
    });
  }

  getPic(event) {
    this.commonForm.patchValue({
      avatar: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
    })
  }


  setBuildFormValue(user: User) {
    this.commonForm.setValue({
      dept: {
        id: user.dept.id,
        deptName: user.dept.deptName,
      },
      username: user.username,
      password: user.password,
      fullName: user.fullName,
      phone: user.phone,
      email: user.email,
      jobNo: user.jobNo,
      avatar: user.avatar,
      state: user.state,
      roles: user.roles,
    });
    //设置部门
    this.deptModalSelect.dataRetrieval(user.dept)
    //设置角色
    this.roleSelect.dataRetrieval(user.roles);
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


  setUserDept(event) {
    if(event.deptName){
      this.commonForm.patchValue({
        dept: {
          id: event.id,
          deptName: event.deptName,
        }
      });
    }
  }

  setRole(event) {
    this.commonForm.patchValue({
      roles: event.map(e=>{
        return {id: e.id,name: e.name}
      })
    });
  }


}
