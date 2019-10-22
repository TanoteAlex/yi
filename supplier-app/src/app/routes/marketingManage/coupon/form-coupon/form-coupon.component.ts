import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Coupon} from '../../../models/original/coupon.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";

@Component({
    selector: 'form-coupon',
    templateUrl: './form-coupon.component.html',
    styleUrls: ['./form-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None,

})
export class FormCouponComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';
    date1: Date[] = [new Date, new Date]
    dateFormat = 'yyyy/MM/dd';
    @Input() coupon: Coupon = new Coupon();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    timeSlot = false

    formErrors = {


        couponName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        couponType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        parValue: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        quantity: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        useQuantity: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        useCondition: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        receiveMode: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        memberLevelId: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        limited: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        validType: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        startTime: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        endTime: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        fixedDay: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        threshold: [],

        commodities: [],
        timeSlot: [],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.coupon !== undefined && value.coupon.currentValue !== undefined) {
            this.setBuildFormValue(this.coupon);
        }
    }

    ngOnInit() {

    }
    bb: boolean = false;
    buildForm(): void {
        this.commonForm = this.fb.group({

            couponName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            couponType: [
                1, Validators.compose([Validators.required,
                    Validators.min(1), Validators.max(2)
                ])
            ],
            parValue: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            quantity: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            useQuantity: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            useCondition: [
                0, Validators.compose([Validators.required,
                    Validators.min(0)
                ])

            ],
            receiveMode: [
                1, Validators.compose([Validators.required,
                    Validators.min(1), Validators.max(2)
                ])
            ],
            limited: [
            ],
            validType: [
                1, Validators.compose([Validators.required,
                    Validators.min(1), Validators.max(2)
                ])
            ],
            startTime: [
            ],
            endTime: [
            ],
            fixedDay: [
            ],
            threshold: [],
            commodities: [[]],
            timeSlot: [[]]
        });
        this.selectCommodityName = '请选择'
        this.bb = this.commonForm.value.validType != 2
    }



    /* /!**
      * 时间段中转
      * @type {number}
      *!/
     transfer=0;*/
    selectCommodityName = '请选择'
    selection: boolean = false;
    setBuildFormValue(coupon: Coupon) {

        this.commonForm.setValue({

            couponName:
            coupon.couponName
            ,
            couponType:
            coupon.couponType
            ,
            parValue:
            coupon.parValue
            ,
            quantity:
            coupon.quantity
            ,
            useQuantity:
            coupon.useQuantity
            ,
            useCondition:
            coupon.useCondition
            ,
            receiveMode:
            coupon.receiveMode
            ,
            limited:
            coupon.limited
            ,
            validType:
            coupon.validType
            ,
            startTime:
            coupon.startTime
            ,
            endTime:
            coupon.endTime
            ,
            fixedDay:
            coupon.fixedDay
            ,
            timeSlot: null,
            threshold: coupon.useCondition
            ,
            commodities: coupon.commodities
        });

        if (this.commonForm.value.couponType) {
            this.voucherType = this.couponType[this.commonForm.value.couponType - 1].key
        }


        if (this.commonForm.value.commodities.length > 0) {
            this.selectCommodityName = ""
            this.commonForm.value.commodities.forEach((e, index) => {
                if (this.commonForm.value.commodities.length != index + 1) {
                    this.selectCommodityName += e.commodityName + ","
                } else {
                    this.selectCommodityName += e.commodityName
                }
            })
        }
        /*   alert(this.commonForm.value.startTime)
           alert(this.commonForm.value.endTime)*/
        if (this.commonForm.value.startTime != null && this.commonForm.value.endTime != null) {

            this.commonForm.patchValue({
                timeSlot: [new Date(this.commonForm.value.startTime), new Date(this.commonForm.value.endTime)]
            })
        }


    }

    //不限制
    unrestricted() {
        this.selection = false;
        this.commonForm.patchValue({
            useCondition: 0
        });
        /*this.commonForm.patchValue({
            useCondition:""
        });*/
    }

    //限制
    limit() {
        this.selection = true;
    }

    faceValue: number;

    fullSubtraction(e) {
        this.faceValue = e
        this.commonForm.patchValue({
            useCondition: e
        });
        // this.reduce=e
    }




    fixed(e) {

    }


    voucherType = "请选择"
    selectValue(type, value) {
        this.commonForm.get(type).setValue(value);
    }

    couponType = [{
        key: "满减券",
        value: 1
    }, {
            key: "买送券",
            value: 2
        }];


    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
        if (commonFormValid) {
            return this.commonForm.value;
        }
        return null;
    }
    @ViewChild("rangePicker") rangePicker: NzRangePickerComponent
    
    haha(dates) {
        this.commonForm.patchValue({
            startTime: dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00",
            endTime: dates[1].toLocaleDateString().replace(/\//g, '-') + " 23:59:59",
        })
    }

    onSubmit() {
        /* console.log(this.date1[0])*/
        /* const formValue = this.submitCheck();*/
        const formValue = this.commonForm.value

        if (this.coupon) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if (this.commonForm.value.validType == 1) {
            this.commonForm.value.fixedDay = ""
        } else if (this.commonForm.value.validType == 2) {
            this.commonForm.value.startTime = "";
            this.commonForm.value.endTime = "";
        }

        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }


    setCommoditySupplier(event) {
        this.commonForm.patchValue({
            commodities: event.map(e => {
                return { id: e.id, commodityName: e.commodityName, commodityNo: e.commodityNo, imgPath: e.imgPath }
            })
        });
    }


}
