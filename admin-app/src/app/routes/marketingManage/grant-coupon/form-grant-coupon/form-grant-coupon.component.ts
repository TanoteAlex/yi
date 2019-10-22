/*
import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Coupon} from '../../../models/original/coupon.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";
import {PageQuery} from "../../../models/page-query.model";
import {MemberLevelService} from "../../../services/member-level.service";

@Component({
  selector: 'form-grant-coupon',
  templateUrl: './form-grant-coupon.component.html',
  styleUrls: ['./form-grant-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None,

})
export class FormGrantCouponComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';
  date1: Date[] = [new Date, new Date]
  dateFormat = 'yyyy/MM/dd';
  @Input() coupon: Coupon = new Coupon();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @Input() pageQuery = new PageQuery().addOnlyFilter('state', 2, 'eq');

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

    receiveMode: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    memberLevel: [
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
    commodities: [],
    timeSlot: [],
    useConditionType:[],
    fullMoney:[],
    fullQuantity:[]
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService,public  memberLevelService:MemberLevelService) {
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
      memberLevel: {
        id: event.id,
        name: event.name
      },
      memberLevelName: event.name
    });
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
        0, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],

      receiveMode: [
        1, Validators.compose([Validators.required,
          Validators.min(1), Validators.max(3)
        ])
      ],
      limited: [1],
      validType: [
        1, Validators.compose([Validators.required,
          Validators.min(1), Validators.max(2)
        ])
      ],
      startTime: [],
      endTime: [],
      fixedDay: [],
      commodities: [[]],
      timeSlot: [[]],
      memberLevel:this.fb.group({
        id:0,
        name:"请选择"
      }),
      useConditionType:[],
      fullMoney:[],
      fullQuantity:[]

    });
    this.selectCommodityName = '请选择'
    this.bb = this.commonForm.value.validType != 2
  }


  /!* /!**
    * 时间段中转
    * @type {number}
    *!/
   transfer=0;*!/
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

      receiveMode:
      coupon.receiveMode
      ,
      memberLevel:{
        id:coupon.memberLevel.id,
        name:coupon.memberLevel.name
      }
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

      commodities: coupon.commodities,
      useConditionType: coupon.useConditionType,
      fullMoney: coupon.fullMoney,
      fullQuantity: coupon.fullQuantity
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
    /!*   alert(this.commonForm.value.startTime)
       alert(this.commonForm.value.endTime)*!/
    if (this.commonForm.value.startTime != null && this.commonForm.value.endTime != null) {
      this.commonForm.patchValue({
        timeSlot: [new Date(this.commonForm.value.startTime), new Date(this.commonForm.value.endTime)]
      })
    }
  }

  //不限制
/!*  unrestricted() {
    this.selection = false;
    this.commonForm.patchValue({
      useCondition: 0
    });
    /!*this.commonForm.patchValue({
        useCondition:""
    });*!/
  }*!/

  //限制
  limit() {
    this.selection = true;
  }

  faceValue: number;

/!*  fullSubtraction(e) {
    this.faceValue = e
    this.commonForm.patchValue({
      useCondition: e
    });
    // this.reduce=e
  }*!/


 /!* fixed(e) {
      this.commonForm.value.threshold=e;
  }
*!/

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

  haha(e) {
    if(e!=null) {
      let split = e[0].toLocaleDateString().split('/');
      let split1 = e[1].toLocaleDateString().split('/');
      this.commonForm.patchValue({
        startTime: split[0] + "-" + split[1] + "-" + split[2] + " 00:00:00",
        endTime: split1[0] + "-" + split1[1] + "-" + split1[2] + " 00:00:00",
      })
    }
  }
  onSubmit() {
    const formValue = this.submitCheck();
    // const formValue=this.commonForm.value
    if (this.coupon) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    if(this.commonForm.value.useConditionType ==null){
      this.msgSrv.warning('请选择使用门槛！');
      return;

    }
    if(this.commonForm.value.useConditionType == 1){
       if(this.commonForm.value.fullMoney==null){
         this.msgSrv.warning('请输入满多少元可用！');
         return;
       }
      this.commonForm.value.fullQuantity==null;
     }
     if(this.commonForm.value.useConditionType == 2){
       if(this.commonForm.value.fullQuantity==null){
         this.msgSrv.warning('请输入满多少件可用！');
         return;
       }
       this.commonForm.value.fullMoney==null;
     }


    if(this.commonForm.value.memberLevel.id==0){
      this.msgSrv.warning('请添加会员！');
      return;
    }
    if (this.commonForm.value.validType == 1) {
      if(this.commonForm.value.startTime == null && this.commonForm.value.endTime == null){
        this.msgSrv.warning('请选择时间段！');
        return;
      }
      this.commonForm.value.fixedDay = ""
    } else if (this.commonForm.value.validType == 2) {
      if(this.commonForm.value.fixedDay == null){
        this.msgSrv.warning('固定天数不能为空！');
        return;
      }
      this.commonForm.value.startTime = "";
      this.commonForm.value.endTime = "";
    }
    this.onTransmitFormValue.emit({obj: formValue});
  }
  reset() {

  }
  goBack() {
    this.location.back();
  }
  setCommoditySupplier(event) {
    this.commonForm.patchValue({
      commodities: event.map(e => {
        return {id: e.id, commodityName: e.commodityName}
      })
    });
  }


}
*/
