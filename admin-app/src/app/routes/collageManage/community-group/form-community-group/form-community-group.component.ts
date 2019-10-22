import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CommunityGroup} from '../../../models/original/community-group.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {ProductService} from "../../../services/product.service";
import {CouponService} from "../../../services/coupon.service";
import {CommunityService} from "../../../services/community.service";

@Component({
    selector: 'form-community-group',
    templateUrl: './form-community-group.component.html',
    styleUrls: ['./form-community-group.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class FormCommunityGroupComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    dateFormat = 'yyyy/MM/dd';

    @Input() communityGroup: CommunityGroup = new CommunityGroup();

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
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        community: [
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

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public productService: ProductService, public couponService: CouponService, public communityService: CommunityService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.communityGroup !== undefined && value.communityGroup.currentValue !== undefined) {
            this.setBuildFormValue(this.communityGroup);
        }
    }

    ngOnInit() {

    }

    @ViewChild("rangePicker") rangePicker: NzRangePickerComponent

    selectTime(dates) {
        if (dates != null) {
            this.commonForm.patchValue({
                startTime: dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00",
                endTime: dates[0].toLocaleDateString().replace(/\//g, '-') + " 00:00:00",
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

    setCommunity(event) {
        this.commonForm.patchValue({
            community: {
                id: event.id,
                address: event.address,
            }
        });
    }

    setCoupon(event) {
        this.commonForm.patchValue({
            coupon: {
                id: event.id,
                couponNo: event.couponNo,
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
            community: this.fb.group({
                id: null,
                address: [
                    "请选择",
                ]
            }),
            activityStock: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            groupPrice: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            groupPeople: [
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
        });
    }

    couponId = 0;
    couponName = "请选择";

    setBuildFormValue(communityGroup: CommunityGroup) {
        if (communityGroup.coupon != null) {
            this.couponId = communityGroup.coupon.id;
            this.couponName = communityGroup.coupon.couponName;
        }
        this.commonForm.setValue({
            label:
            communityGroup.label
            ,
            startTime:
            communityGroup.startTime
            ,
            endTime:
            communityGroup.endTime
            ,
            activityCover:
            communityGroup.activityCover
            ,
            shareTitle:
            communityGroup.shareTitle
            ,
            product: {
                id: communityGroup.product.id,
                productName: communityGroup.product.productName,
            },
            community: {
                id: communityGroup.community.id,
                address: communityGroup.community.address,
            },
            activityStock:
            communityGroup.activityStock
            ,
            groupPrice:
            communityGroup.groupPrice
            ,
            groupPeople:
            communityGroup.groupPeople
            ,
            limitGroupTime:
            communityGroup.limitGroupTime
            ,
            limitQuantity:
            communityGroup.limitQuantity
            ,
            limitPayTime:
            communityGroup.limitPayTime
            ,
            rewardType:
            communityGroup.rewardType
            ,
            rewardIntegral:
            communityGroup.rewardIntegral
            ,
            coupon: {
                id: this.couponId,
                couponName: this.couponName,
            },
            freightSet:
            communityGroup.freightSet
            ,
            freight:
            communityGroup.freight
            ,
            state:
            communityGroup.state
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
        if (this.communityGroup) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
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
        if (this.commonForm.value.community.address == "请选择") {
            this.msgSrv.warning('请选择小区！');
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
