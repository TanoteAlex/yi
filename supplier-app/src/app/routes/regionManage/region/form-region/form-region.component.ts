import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Region} from '../../../models/original/region.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-region',
  templateUrl: './form-region.component.html',
  styleUrls: ['./form-region.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRegionComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() region: Region =new Region();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {


        province:[
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        city:[
            {
                name: 'maxlength',
                msg: '最大8位长度',
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
        if (value.region !== undefined && value.region.currentValue !== undefined) {
            this.setBuildFormValue(this.region);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({

            province: [
              null, Validators.compose([Validators.required,
                Validators.maxLength(127)
              ])
            ],
            city: [
              null, Validators.compose([Validators.required,
                Validators.maxLength(127)
              ])
            ],
            state: [
              0, Validators.compose([Validators.required,
                Validators.min(0), Validators.max(1)
              ])
            ],
        });
    }

    setBuildFormValue(region: Region) {
        this.commonForm.setValue({
            province:
            region.province
                ,
            city:
            region.city
                ,
            state:
            region.state
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
 if (this.region) {
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
