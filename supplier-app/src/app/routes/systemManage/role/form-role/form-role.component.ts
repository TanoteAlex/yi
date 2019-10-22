import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Role} from '../../../models/original/role.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-role',
  templateUrl: './form-role.component.html',
  styleUrls: ['./form-role.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRoleComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() role: Role =new Role();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {
        name:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        description:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        state:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],

    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.role !== undefined && value.role.currentValue !== undefined) {
            this.setBuildFormValue(this.role);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({

            name: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            description: [
            ],
       /*     state: [
              0, Validators.compose([Validators.required,
                Validators.min(0), Validators.max(1)
              ])
            ],*/

        });
    }

    setBuildFormValue(role: Role) {
        this.commonForm.setValue({

            name:
            role.name
                ,
            description:
            role.description,

           /* state:
            role.state
                ,*/
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
 if (this.role) {
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
