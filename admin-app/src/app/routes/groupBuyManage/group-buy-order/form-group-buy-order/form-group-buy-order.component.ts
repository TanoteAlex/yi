

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {GroupBuyOrderBo} from '../../../models/domain/bo/group-buy-order-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-group-buy-order',
  templateUrl: './form-group-buy-order.component.html',
  styleUrls: ['./form-group-buy-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormGroupBuyOrderComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() groupBuyOrder: GroupBuyOrderBo =new GroupBuyOrderBo();

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
        groupBuyActivityProduct:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        saleOrder:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
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
        startTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        endTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        remark:[
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        createTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        deleted:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        delTime:[
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
        if (value.groupBuyOrder !== undefined && value.groupBuyOrder.currentValue !== undefined) {
            this.setBuildFormValue(this.groupBuyOrder);
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
            groupBuyActivityProduct: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            saleOrder: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            startTime: [
            ],
            endTime: [
            ],
            remark: [
            ],
            createTime: [
            ],
            deleted: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            delTime: [
            ],
        });
    }

    setBuildFormValue(groupBuyOrder: GroupBuyOrderBo) {
        this.commonForm.setValue({
            guid:
            groupBuyOrder.guid
                ,
            member:
            groupBuyOrder.member
                ,
            groupBuyActivityProduct:
            groupBuyOrder.groupBuyActivityProduct
                ,
            saleOrder:
            groupBuyOrder.saleOrder
                ,
            state:
            groupBuyOrder.state
                ,
            startTime:
            groupBuyOrder.startTime
                ,
            endTime:
            groupBuyOrder.endTime
                ,
            remark:
            groupBuyOrder.remark
                ,
            createTime:
            groupBuyOrder.createTime
                ,
            deleted:
            groupBuyOrder.deleted
                ,
            delTime:
            groupBuyOrder.delTime
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
 if (this.groupBuyOrder) {
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
