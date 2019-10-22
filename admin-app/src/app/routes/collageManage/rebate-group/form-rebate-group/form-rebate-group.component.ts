import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RebateGroup} from '../../../models/original/rebate-group.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {ProductService} from "../../../services/product.service";
import {CouponService} from "../../../services/coupon.service";
import {log} from "util";

@Component({
    selector: 'form-rebate-group',
    templateUrl: './form-rebate-group.component.html',
    styleUrls: ['./form-rebate-group.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class FormRebateGroupComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    dateFormat = 'yyyy/MM/dd';

    @Input() rebateGroup: RebateGroup = new RebateGroup();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        id: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        guid: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
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
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        shareTitle: [
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
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        activityStock: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        limitGroupTime: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        limitQuantity: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        limitPayTime: [
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
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        coupon: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
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
        rebateSets: [],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public productService: ProductService, public couponService: CouponService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.rebateGroup !== undefined && value.rebateGroup.currentValue !== undefined) {
            this.setBuildFormValue(this.rebateGroup);
        }
    }


    ngOnInit() {
    }

    isAdd = true;

    addRebate() {
        this.commonForm.value.rebateSets.forEach(e => {
            if (e.groupPeople == 0 || e.groupPrice == 0) {
                this.msgSrv.warning('拼团人数或拼团价不能为空！');
                this.isAdd = false;
                return;
            } else {
                this.isAdd = true;
            }
        }
        )
        if (this.isAdd) {
            let rebateSets: FormArray = this.commonForm.get('rebateSets') as FormArray;
            rebateSets.push(this.fb.group({
                groupPeople: [0],
                groupPrice: [0]
            }))
        }
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

    activityCoverPic = "";

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
            label: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(255)
                ])
            ],
            startTime: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(19)
                ])
            ],
            endTime: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(19)
                ])
            ],
            activityCover: [null],
            shareTitle: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(255)
                ])
            ],
            product: this.fb.group({
                id: null,
                productName: [
                    "请选择",
                ]
            }),
            activityStock: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            limitGroupTime: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            limitQuantity: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            limitPayTime: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            rewardType: [
                1, Validators.compose([Validators.required,
                    Validators.min(1), Validators.max(2)
                ])
            ],
            rewardIntegral: [0],
            coupon: this.fb.group({
                id: null,
                couponName: [
                    "请选择",
                ]
            }),
            freightSet: [
                1, Validators.compose([Validators.required,
                    Validators.min(1), Validators.max(2)
                ])
            ],
            freight: [
                0, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            state: [
                0, Validators.compose([Validators.required,
                    Validators.min(0), Validators.max(1)
                ])
            ],
            timeSlot: [[]],
            rebateSets: this.fb.array([this.fb.group(
                {
                    groupPeople: [0],
                    groupPrice: [0]
                })]),
        });
    }

    couponId = 0;
    couponName = "请选择";

    setBuildFormValue(rebateGroup: RebateGroup) {
        if (rebateGroup.coupon != null) {
            this.couponId = rebateGroup.coupon.id;
            this.couponName = rebateGroup.coupon.couponName;
        }

        let rebateSets: FormArray = this.commonForm.get('rebateSets') as FormArray;
        rebateGroup.rebateSets.forEach((e, index) => {
            let value = { groupPeople: e.groupPeople, groupPrice: e.groupPrice }

            if (index == 0) {
                rebateSets.get('0').patchValue(value)
            } else {
                rebateSets.push(this.fb.group({
                    groupPeople: [e.groupPeople],
                    groupPrice: [e.groupPrice],
                }))
            }
        })


        this.commonForm.setValue({
            label:
            rebateGroup.label
            ,
            startTime:
            rebateGroup.startTime
            ,
            endTime:
            rebateGroup.endTime
            ,
            activityCover:
            rebateGroup.activityCover
            ,
            shareTitle:
            rebateGroup.shareTitle
            ,
            product: {
                id: rebateGroup.product.id,
                productName: rebateGroup.product.productName,
            },
            activityStock:
            rebateGroup.activityStock
            ,
            limitGroupTime:
            rebateGroup.limitGroupTime
            ,
            limitQuantity:
            rebateGroup.limitQuantity
            ,
            limitPayTime:
            rebateGroup.limitPayTime
            ,
            rewardType:
            rebateGroup.rewardType
            ,
            rewardIntegral:
            rebateGroup.rewardIntegral
            ,
            coupon: {
                id: this.couponId,
                couponName: this.couponName,
            },
            freightSet:
            rebateGroup.freightSet
            ,
            freight:
            rebateGroup.freight
            ,
            state:
            rebateGroup.state
            ,
            timeSlot: null,
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
        if (this.rebateGroup) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }

        if (this.commonForm.value.activityCover == null) {
            this.msgSrv.warning('请添加活动封面图片！');
            return;
        }
        // this.commonForm.value.c = this.activityCoverPic;

        if (this.commonForm.value.product.productName == "请选择") {
            this.msgSrv.warning('请选择拼团货品！');
            return;
        }
        if (this.commonForm.value.rewardType == 1) {
            if (this.commonForm.value.rewardIntegral == 0) {
                this.msgSrv.warning('请填写赠送积分！');
                return;
            }
            if (this.commonForm.value.coupon.couponName == "请选择") {
                this.commonForm.value.coupon = null;
            }
            this.commonForm.value.coupon = null;
        } else if (this.commonForm.value.rewardType == 2) {
            if (this.commonForm.value.coupon.couponName == "请选择") {
                this.msgSrv.warning('请选择优惠券！');
                return;
            }
        }
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
