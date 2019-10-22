import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CouponGrantConfigBo} from '../../../models/domain/bo/coupon-grant-config-bo.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CouponService} from "../../../services/coupon.service";
import {CommodityService} from "../../../services/commodity.service";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
  selector: 'form-voucher-grant-config',
  templateUrl: './form-voucher-grant-config.component.html',
  styleUrls: ['./form-voucher-grant-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormVoucherGrantConfigComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() couponGrantConfig: CouponGrantConfigBo = new CouponGrantConfigBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  couponPageQuery: PageQuery = new PageQuery();
  commodityPageQuery: PageQuery = new PageQuery();

  couponGrantSteps: FormGroup;

  @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;
  @ViewChild('couponModalSelect') couponModalSelect: ModalSelecetComponent;

  formErrors = {

    coupon: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    grantStrategy: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    grantNode: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    thawNode: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    thawDays: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大4位长度',
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
    remark: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    commodities: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    couponGrantSteps: [],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public couponService: CouponService, public commodityService: CommodityService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.couponGrantConfig !== undefined && value.couponGrantConfig.currentValue !== undefined) {
      this.setBuildFormValue(this.couponGrantConfig);
    }
  }

  ngOnInit() {
    this.couponPageQuery.addOnlyOneFilter("couponType", 2, "eq");
    this.commodityPageQuery.addOnlyOneFilter('audditState', 2, 'eq');
    this.commodityPageQuery.addOnlyOneFilter('shelfState', 1, 'eq');
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      coupon: [null, Validators.compose([Validators.required])],
      grantStrategy: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
      grantNode: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(4)])],
      thawNode: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(4)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      remark: [null, Validators.compose([Validators.maxLength(255)])],
      freezeDays: [null, Validators.compose([Validators.required,Validators.maxLength(4)])],
      commodities: [null, Validators.compose([Validators.required])],
      couponGrantSteps: this.fb.array([]),
    });
    this.setCouponGrantStepsFormArray(true);
  }

  setBuildFormValue(couponGrantConfig: CouponGrantConfigBo) {
    this.couponGrantStepArray.forEach(e => {
      couponGrantConfig.couponGrantSteps.forEach(e1 => {
        if (e.level == e1.grantNode) {
          e.id = e1.id;
          e.value = e1.grantRate;
          e1.grantTitle = e.grantTitle;
        }
      })
    })
    this.commonForm.patchValue({
      coupon: couponGrantConfig.coupon,
      grantStrategy: couponGrantConfig.grantStrategy,
      grantNode: couponGrantConfig.grantNode,
      thawNode: couponGrantConfig.thawNode,
      state: couponGrantConfig.state,
      remark: couponGrantConfig.remark,
      commodities: couponGrantConfig.commodities,
      couponGrantSteps: couponGrantConfig.couponGrantSteps,
      freezeDays: couponGrantConfig.freezeDays,
    });
    this.setCouponGrantStepsFormArray();
    this.commodityModalSelect.dataRetrieval(couponGrantConfig.commodities);
    this.couponModalSelect.dataRetrieval(couponGrantConfig.coupon);
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
    // if(formValue.grantStrategy == 1){
    //   formValue.couponGrantSteps = null;
    // }
    // console.log("commonForm value=" + JSON.stringify(formValue));
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  setCoupon(event) {
    if (event.couponName) {
      this.commonForm.patchValue({
        coupon: {
          id: event.id,
          couponName: event.couponName
        }
      })
    }
  }

  setCommodity(event) {
    this.commonForm.patchValue({
      commodities: event.map(e => {
        return {id: e.id, commodityName: e.commodityName, commodityNo: e.commodityNo, imgPath: e.imgPath}
      })
    });
  }

  couponGrantStepArray = [
    {id: null, grantTitle: "下单", value: 0, couponGrantConfig: null, level: 1},
    {id: null, grantTitle: "收货", value: 0, couponGrantConfig: null, level: 2},
    {id: null, grantTitle: "评论", value: 0, couponGrantConfig: null, level: 3},
    {id: null, grantTitle: "超过三包时效", value: 0, couponGrantConfig: null, level: 4},
  ]

  setCouponGrantStepsFormArray(init?) {
    let couponGrantStepsFormArray: FormArray = new FormArray([]);
    this.couponGrantStepArray.forEach(e => {
      couponGrantStepsFormArray.push(this.fb.group({
        id: init ? null : e.id,
        guid:null,
        grantTitle: e.grantTitle,
        grantNode: e.level,
        grantRate: e.value,
      }))
    })
    this.commonForm.setControl('couponGrantSteps', couponGrantStepsFormArray)
  }

}
