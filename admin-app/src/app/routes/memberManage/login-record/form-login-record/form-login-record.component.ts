

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginRecordBo } from '../../../models/domain/bo/login-record-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-login-record',
  templateUrl: './form-login-record.component.html',
  styleUrls: ['./form-login-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormLoginRecordComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() loginRecord: LoginRecordBo =new LoginRecordBo();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        id:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        guid:[
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        member:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        loginIp:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        loginSource:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        sessionId:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        loginTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        logoutTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.loginRecord !== undefined && value.loginRecord.currentValue !== undefined) {
            this.setBuildFormValue(this.loginRecord);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
            ],
            member: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            loginIp: [
            ],
            loginSource: [
            ],
            sessionId: [
            ],
            loginTime: [
            ],
            logoutTime: [
            ],
        });
    }

    setBuildFormValue(loginRecord: LoginRecordBo) {
        this.commonForm.setValue({
            guid:
            loginRecord.guid
                ,
            member:
            loginRecord.member
                ,
            loginIp:
            loginRecord.loginIp
                ,
            loginSource:
            loginRecord.loginSource
                ,
            sessionId:
            loginRecord.sessionId
                ,
            loginTime:
            loginRecord.loginTime
                ,
            logoutTime:
            loginRecord.logoutTime
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
 if (this.loginRecord) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack(){
        this.location.back();
    }

}
