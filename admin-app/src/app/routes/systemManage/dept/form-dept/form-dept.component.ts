import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dept} from '../../../models/original/dept.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {DeptService} from "../../../services/dept.service";
import {PageQuery} from "../../../models/page-query.model";
import {DeptTreeSelectComponent} from "../../../components/dept-tree-select/dept-tree-select.component";

@Component({
    selector: 'form-dept',
    templateUrl: './form-dept.component.html',
    styleUrls: ['./form-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormDeptComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() dept: Dept = new Dept();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('deptTreeSelect') deptTreeSelect: DeptTreeSelectComponent

    formErrors = {

        parent: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        deptNo: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        deptName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        description: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public deptService: DeptService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.dept !== undefined && value.dept.currentValue !== undefined) {
            this.setBuildFormValue(this.dept);
        }
    }

    ngOnInit() {
    }

    parentDept(event) {
        this.commonForm.patchValue({
            parent: {
                id: event.id,
                deptName: event.deptName
            },
        })
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            parent: [null],
            deptNo: [null, Validators.compose([Validators.maxLength(16)])],
            deptName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
            description: [null, Validators.compose([Validators.maxLength(32)])],
        });
    }

    isShowPreDept = true;

    setBuildFormValue(dept: Dept) {
        if (dept.parent == null) {
            this.isShowPreDept = false;
        } else {
            this.deptTreeSelect.select(dept.parent.id)
        }
        this.commonForm.setValue({
            parent: {
                id: dept.parent ? dept.parent.id : 0,
                deptName: dept.parent ? dept.parent.deptName : null,
            }
            ,
            deptName: dept.deptName,
            deptNo: dept.deptNo,
            description: dept.description,
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
        if (this.dept) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }


}
