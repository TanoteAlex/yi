import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Coupon} from '../../../models/original/coupon.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";
import {MemberLevelService} from "../../../services/member-level.service";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";
import {fullMoneyValidator, fullQuantityValidator, timeValidator} from "@shared/custom-validators/custom-validator";

@Component({
    selector: 'form-coupon',
    templateUrl: './form-buy-coupons.component.html',
    styleUrls: ['./form-buy-coupons.component.scss'],
    encapsulation: ViewEncapsulation.None,

})
export class FormBuyCouponsComponent implements OnInit, OnChanges {

    commonForm: FormGroup;
    @Input() title: string = '表单';
    dateFormat = 'yyyy/MM/dd';

    @Input() coupon: Coupon = new Coupon();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('modalSelectCommodity') modalSelectCommodity: ModalSelecetComponent;
    @ViewChild('modalSelectMember') modalSelectMember: ModalSelecetComponent;

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
        receiveMode: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        memberLevels: [
            {
                name: 'required',
                msg: '不可为空',
            },
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
                name: 'timeRequire',
                msg: '请选择时间段',
            },
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
        commodities: [],
        timeSlot: [],
        useConditionType: [],
        fullMoney: [
            {
                name: 'moneyRequire',
                msg: '请输入价格',
            },
        ],
        fullQuantity: [
            {
                name: 'quantityRequire',
                msg: '请输入件数',
            },
        ]
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService, public memberLevelService: MemberLevelService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.coupon !== undefined && value.coupon.currentValue !== undefined) {
            this.setBuildFormValue(this.coupon);
        }
    }

    ngOnInit() {

    }

    setMemberLevel(event) {
        this.commonForm.patchValue({
            memberLevels: event.map(e => {
                return { id: e.id, name: e.name }
            })
        });
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            couponName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
            parValue: [null, Validators.compose([Validators.required, Validators.maxLength(15)])],
            quantity: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
            receiveMode: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(3)])],
            limited: [1],
            validType: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
            startTime: [null],
            endTime: [],
            fixedDay: [1],
            commodities: [[]],
            timeSlot: [[]],
            memberLevels: [null, Validators.compose([Validators.required])],
            useConditionType: [0, Validators.compose([Validators.required])],
            fullMoney: [null],
            fullQuantity: [null]
        });
        //自定义时间表单
        this.commonForm.get('startTime').setValidators(timeValidator(this.commonForm.get('validType')))
        //自定义价格表单
        this.commonForm.get('fullMoney').setValidators(fullMoneyValidator(this.commonForm.get('useConditionType')))
        //自定义件数表单
        this.commonForm.get('fullQuantity').setValidators(fullQuantityValidator(this.commonForm.get('useConditionType')))
    }

    setBuildFormValue(coupon: Coupon) {
        this.commonForm.setValue({
            couponName: coupon.couponName,
            parValue: coupon.parValue,
            quantity: coupon.quantity,
            receiveMode: coupon.receiveMode,
            memberLevels: coupon.memberLevels,
            limited: coupon.limited,
            validType: coupon.validType,
            startTime:
            coupon.startTime,
            endTime: coupon.endTime,
            fixedDay: coupon.fixedDay,
            timeSlot: null,
            commodities: coupon.commodities,
            useConditionType: coupon.useConditionType,
            fullMoney: coupon.fullMoney,
            fullQuantity: coupon.fullQuantity
        });
        this.modalSelectCommodity.dataRetrieval(coupon.commodities)
        this.modalSelectMember.dataRetrieval(coupon.memberLevels)
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

    haha(dates) {
        if (dates != null) {
            this.commonForm.patchValue({
                startTime: dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00",
                endTime: dates[1].toLocaleDateString().replace(/\//g, '-') + " 23:59:59",
            })
        }
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

    setCommoditySupplier(event) {
        this.commonForm.patchValue({
            commodities: event.map(e => {
                return { id: e.id, commodityName: e.commodityName, commodityNo: e.commodityNo, imgPath: e.imgPath }
            })
        });
    }


}
