import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Seckill} from '../../../models/original/seckill.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {ProductService} from "../../../services/product.service";

@Component({
    selector: 'form-seckill',
    templateUrl: './form-seckill.component.html',
    styleUrls: ['./form-seckill.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class FormSeckillComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() seckill: Seckill = new Seckill();

    dateFormat = 'yyyy/MM/dd';

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
                name: 'required',
                msg: '不可为空',
            },
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
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
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
        seckillPrice: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
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
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public productService: ProductService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.seckill !== undefined && value.seckill.currentValue !== undefined) {
            this.setBuildFormValue(this.seckill);
        }
    }

    ngOnInit() {

    }

    @ViewChild("rangePicker") rangePicker: NzRangePickerComponent

    selectTime(dates) {
        if (dates != null) {
            this.commonForm.patchValue({
                startTime: dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00",
                endTime: dates[1].toLocaleDateString().replace(/\//g, '-') + " 23:59:59",
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
            seckillPrice: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
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
        });
    }

    couponId = 0;
    couponName = "请选择";

    setBuildFormValue(seckill: Seckill) {
        if (seckill.coupon != null) {
            this.couponId = seckill.coupon.id;
            this.couponName = seckill.coupon.couponName;
        }
        this.commonForm.setValue({
            label:
            seckill.label
            ,
            startTime:
            seckill.startTime
            ,
            endTime:
            seckill.endTime
            ,
            activityCover:
            seckill.activityCover
            ,
            shareTitle:
            seckill.shareTitle
            ,
            product: {
                id: seckill.product.id,
                productName: seckill.product.productName,
            },
            activityStock:
            seckill.activityStock
            ,
            seckillPrice:
            seckill.seckillPrice
            ,
            limitQuantity:
            seckill.limitQuantity
            ,
            limitPayTime:
            seckill.limitPayTime
            ,
            rewardType:
            seckill.rewardType
            ,
            rewardIntegral:
            seckill.rewardIntegral
            ,
            coupon: {
                id: this.couponId,
                couponName: this.couponName,
            },
            freightSet:
            seckill.freightSet
            ,
            freight:
            seckill.freight
            ,
            state:
            seckill.state
            ,
            timeSlot: null,
        });

        this.activityCoverPic = this.commonForm.value.activityCover;

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
        if (this.seckill) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if (this.activityCoverPic == "") {
            this.msgSrv.warning('请添加活动封面图片！');
            return;
        }
        this.commonForm.value.activityCover = this.activityCoverPic;

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
