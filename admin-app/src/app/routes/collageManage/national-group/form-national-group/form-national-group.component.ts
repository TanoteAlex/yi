import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NationalGroup} from '../../../models/original/national-group.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {ProductService} from "../../../services/product.service";
import {CouponService} from "../../../services/coupon.service";
import {PageQuery} from "../../../models/page-query.model";
import {couponValidator} from "@shared/custom-validators/custom-validator";

@Component({
    selector: 'form-national-group',
    templateUrl: './form-national-group.component.html',
    styleUrls: ['./form-national-group.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class FormNationalGroupComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    pageQuery: PageQuery = new PageQuery();

    @Input() title: string = '表单';

    dateFormat = 'yyyy/MM/dd';

    @Input() nationalGroup: NationalGroup = new NationalGroup();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        label: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        startTime: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        endTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        activityCover: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        shareTitle: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        product: [
            {
                name: 'required',
                msg: '不可为空',
            },
        ],
        activityStock: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        groupPrice: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        groupPeople: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        limitGroupTime: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        limitQuantity: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        limitPayTime: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        rewardType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        rewardIntegral: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        coupon: [
            {
                name: 'couponRequired',
                msg: '不可为空',
            },
        ],
        freightSet: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        freight: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
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
        timeSlot: [],
        stockSet: [],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public productService: ProductService, public couponService: CouponService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.nationalGroup !== undefined && value.nationalGroup.currentValue !== undefined) {
            this.setBuildFormValue(this.nationalGroup);
        }
    }

    ngOnInit() {
        this.pageQuery.addFilter("deleted", "0", "eq");
    }

    @ViewChild("rangePicker") rangePicker: NzRangePickerComponent

    selectTime(dates) {
        if (dates != null) {
            this.commonForm.patchValue({
                startTime: dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00",
                endTime: dates[0].toLocaleDateString().replace(/\//g, '-') + " 23:59:59",
            })
        }
    }

    getPic(event) {
        this.commonForm.patchValue({
            activityCover: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
        })
    }

    setProduct(event) {
        this.commonForm.patchValue({
            product: {
                id: event.id,
                productName: event.productName,
                stock: event.stock,
            }
        });
    }

    setCoupon(event) {
        this.commonForm.patchValue({
            coupon: {
                id: event.id,
                couponName: event.couponName,
            }
        });
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            label: [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
            startTime: [null, Validators.compose([Validators.required, Validators.maxLength(19)])],
            endTime: [],
            activityCover: [null, Validators.compose([Validators.required])],
            shareTitle: [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
            product: [null, Validators.compose([Validators.required])],
            activityStock: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
            groupPrice: [null, Validators.compose([Validators.required, Validators.maxLength(15)])],
            groupPeople: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
            limitGroupTime: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
            limitQuantity: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
            limitPayTime: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
            rewardType: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
            rewardIntegral: [0],
            coupon: [null],
            freightSet: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
            freight: [0, Validators.compose([Validators.required, Validators.maxLength(15)])],
            state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
            timeSlot: [[]],
            stockSet: [1]
        });
        //自定义优惠卷表单
        this.commonForm.get('coupon').setValidators(couponValidator(this.commonForm.get('rewardType')))
    }

    setBuildFormValue(nationalGroup: NationalGroup) {
        this.commonForm.setValue({
            label: nationalGroup.label,
            startTime: nationalGroup.startTime,
            endTime: nationalGroup.endTime,
            activityCover: nationalGroup.activityCover,
            shareTitle: nationalGroup.shareTitle,
            product: {
                id: nationalGroup.product.id,
                productName: nationalGroup.product.productName,
            },
            activityStock: nationalGroup.activityStock,
            groupPrice: nationalGroup.groupPrice,
            groupPeople: nationalGroup.groupPeople,
            limitGroupTime: nationalGroup.limitGroupTime,
            limitQuantity: nationalGroup.limitQuantity,
            limitPayTime: nationalGroup.limitPayTime,
            rewardType: nationalGroup.rewardType,
            rewardIntegral: nationalGroup.rewardIntegral,
            coupon: {
                id: nationalGroup.coupon ? nationalGroup.coupon.id : null,
                couponName: nationalGroup.coupon ? nationalGroup.coupon.couponName : '请选择',
            },
            freightSet: nationalGroup.freightSet,
            freight: nationalGroup.freight,
            state: nationalGroup.state,
            timeSlot: null,
            stockSet: nationalGroup.stockSet
        });
        if (this.commonForm.value.startTime != null && this.commonForm.value.endTime != null) {
            this.commonForm.patchValue({
                timeSlot: [new Date(this.commonForm.value.startTime), new Date(this.commonForm.value.endTime)]
            })
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
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
