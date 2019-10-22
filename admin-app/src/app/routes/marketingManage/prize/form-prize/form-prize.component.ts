import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PrizeBo } from '../../../models/domain/bo/prize-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { PageQuery } from '../../../models/page-query.model';
import { CommodityService } from '../../../services/commodity.service';
import { CouponService } from '../../../services/coupon.service';
import { ModalSelecetComponent } from '../../../components/modal-selecet/modal-selecet.component';
import { prizeTypeCommodityValidator, prizeTypeCouponValidator, prizeTypeIntegralValidator, prizeTypeProductValidator } from '@shared/custom-validators/custom-validator';

@Component({
  selector: 'form-prize',
  templateUrl: './form-prize.component.html',
  styleUrls: ['./form-prize.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FormPrizeComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() prize: PrizeBo = new PrizeBo();
  commodityPageQuery: PageQuery = new PageQuery();
  couponPageQuery: PageQuery = new PageQuery();
  // voucherPageQuery: PageQuery = new PageQuery();

  @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;
  @ViewChild('couponModalSelect') couponModalSelect: ModalSelecetComponent;
  // @ViewChild('voucherModalSelect') voucherModalSelect: ModalSelecetComponent;

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    name: [
      {
        name: 'required',
        msg: '不可为空',
      }, {
        name: 'maxlength',
        msg: '最大32位长度',
      },
    ],
    prizeType: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    integral: [
      {
        name: 'integralRequire',
        msg: '不可为空',
      },
    ],
    commodity: [
      {
        name: 'commodityRequire',
        msg: '不可为空',
      },
    ],
    coupon: [
      {
        name: 'couponRequire',
        msg: '不可为空',
      },
    ],
    // voucher: [
    //   {
    //     name: 'maxlength',
    //     msg: '最大10位长度',
    //   }
    // ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      },
    ],
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      },
    ],
    reminder: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService, public couponService: CouponService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.prize !== undefined && value.prize.currentValue !== undefined) {
      this.setBuildFormValue(this.prize);
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addFilter('auditState', 2, 'eq');
    this.commodityPageQuery.addFilter('shelfState', 1, 'eq');
    this.couponPageQuery.addOrFilter('couponType', 1, 'eq');
    this.couponPageQuery.addOrFilter('couponType', 2, 'eq');
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      name: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      prizeType: [1, Validators.compose([Validators.required])],
      integral: [0],
      commodity: [],
      coupon: [],
      // voucher: [],
      state: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
      remark: [null, Validators.compose([Validators.maxLength(127)])],
      reminder: [null, Validators.compose([Validators.maxLength(127)])],
    });
    this.commonForm.get('integral').setValidators(prizeTypeIntegralValidator(this.commonForm.get('prizeType')));
    this.commonForm.get('commodity').setValidators(prizeTypeCommodityValidator(this.commonForm.get('prizeType')));
    this.commonForm.get('coupon').setValidators(prizeTypeCouponValidator(this.commonForm.get('prizeType')));
    this.commonForm.controls['prizeType'].valueChanges.subscribe(data => {
      if (data) {
        switch (data) {
          case 1:
            this.commonForm.patchValue({
              commodity: null,
              product: null,
              coupon: null,
            });
            break;
          case 2:
            this.commonForm.patchValue({
              integral: null,
              coupon: null,
            });
            break;
          case 3:
            this.commonForm.patchValue({
              integral: null,
              commodity: null,
              product: null,
            });
            break;
        }
      }
    });
  }

  setBuildFormValue(prize: PrizeBo) {
    // if(prize.prizeType == 4){
    //   prize.voucher = prize.coupon;
    //   prize.coupon = null;
    // }
    this.commonForm.setValue({
      name: prize.name,
      prizeType: prize.prizeType,
      integral: prize.integral,
      commodity: prize.commodity,
      coupon: prize.coupon,
      // voucher: null,
      state: prize.state,
      remark: prize.remark,
      reminder: prize.reminder,
    });
    // if(prize.prizeType == 4){
    //   this.commonForm.patchValue({
    //     voucher: prize.voucher,
    //   })
    // }
    //设置商品
    if(prize.commodity){
      this.commodityModalSelect.dataRetrieval(prize.commodity);
    }
    //设置优惠券
    if(prize.coupon){
      this.couponModalSelect.dataRetrieval(prize.coupon);
    }
    //设置代金券
    // this.voucherModalSelect.dataRetrieval(prize.voucher);
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
    // if(this.commonForm.value.prizeType == 4){
    //   formValue.coupon = this.commonForm.value.voucher
    // }
    this.onTransmitFormValue.emit({ obj: formValue });
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  setCommodity(event) {
    if (event.commodityName != null && event.length != 0) {
      this.commonForm.patchValue({
        commodity: {
          id: event.id,
          commodityShortName: event.commodityShortName,
        },
      });
    }
  }

  setCoupon(event) {
    if (event.couponName != null && event.length != 0) {
      this.commonForm.patchValue({
        coupon: {
          id: event.id,
          couponName: event.couponName,
        },
      });
    }
  }

  // setVoucher(event) {
  //   if (event.couponName != null && event.length != 0) {
  //     this.commonForm.patchValue({
  //       voucher: {
  //         id: event.id,
  //         couponName: event.couponName,
  //       }
  //     });
  //   }
  // }

}
