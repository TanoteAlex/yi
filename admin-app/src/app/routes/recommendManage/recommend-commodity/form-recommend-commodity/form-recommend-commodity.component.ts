

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RecommendCommodityBo } from '../../../models/domain/bo/recommend-commodity-bo.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
    selector: 'form-recommend-commodity',
    templateUrl: './form-recommend-commodity.component.html',
    styleUrls: ['./form-recommend-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormRecommendCommodityComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() recommendCommodity: RecommendCommodityBo = new RecommendCommodityBo();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        recommendId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        commodityId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        sort: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.recommendCommodity !== undefined && value.recommendCommodity.currentValue !== undefined) {
            this.setBuildFormValue(this.recommendCommodity);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            id: [],
            recommendId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            commodityId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            sort: [
            ],
        });
    }

    setBuildFormValue(recommendCommodity: RecommendCommodityBo) {
        this.commonForm.setValue({
            id: 0,
            recommendId:
            recommendCommodity.recommendId
            ,
            commodityId:
            recommendCommodity.commodityId
            ,
            sort:
            recommendCommodity.sort
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
        if (this.recommendCommodity) {
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
