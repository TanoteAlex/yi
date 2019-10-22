import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InvitePrizeBo } from '../../../models/domain/bo/invite-prize-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { InviteActivityService } from '../../../services/invite-activity.service';
import { CommodityService } from '../../../services/commodity.service';
import { ProductService } from '../../../services/product.service';
import { CouponService } from '../../../services/coupon.service';
import { ModalSelecetComponent } from '../../../components/modal-selecet/modal-selecet.component';
import { PageQuery } from '../../../models/page-query.model';
import {
  articleValidator,
  productValidator,
  prizeTypeProductValidator,
  prizeTypeCouponValidator,
  prizeTypeIntegralValidator,
} from '@shared/custom-validators/custom-validator';

@Component({
  selector: 'form-invite-prize',
  templateUrl: './form-invite-prize.component.html',
  styleUrls: ['./form-invite-prize.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [InviteActivityService, CommodityService, ProductService, CouponService],
})
export class FormInvitePrizeComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  commodityPageQuery: PageQuery = new PageQuery();

  productPageQuery: PageQuery = new PageQuery();

  couponPageQuery: PageQuery = new PageQuery();

  @Input() title: string = '表单';

  @Input() invitePrize: InvitePrizeBo = new InvitePrizeBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('inviteActivityModalSelect') inviteActivityModalSelect: ModalSelecetComponent;

  @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;

  @ViewChild('couponModalSelect') couponModalSelect: ModalSelecetComponent;

  @ViewChild('productModalSelect') productModalSelect: ModalSelecetComponent;

  prizeTypes = [{
    code: 1,
    title: '积分',
  }, {
    code: 2,
    title: '商品',
  }, {
    code: 3,
    title: '优惠券',
  }];

  formErrors = {
    inviteActivity: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    prizeNo: [
      {
        name: 'required',
        msg: '不可为空',
      }, {
        name: 'maxlength',
        msg: '最大16位长度',
      },
    ],
    prizeName: [
      {
        name: 'required',
        msg: '不可为空',
      }, {
        name: 'maxlength',
        msg: '最大32位长度',
      },
    ],
    inviteNum: [
      {
        name: 'required',
        msg: '不可为空',
      }, {
        name: 'maxlength',
        msg: '最大3位长度',
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
    product: [
      {
        name: 'productRequire',
        msg: '不可为空',
      },
    ],
    coupon: [
      {
        name: 'couponRequire',
        msg: '不可为空',
      },
    ],
    sort: [
      {
        name: 'required',
        msg: '不可为空',
      }, {
        name: 'maxlength',
        msg: '最大0位长度',
      },
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大0位长度',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public inviteActivityService: InviteActivityService, public commodityService: CommodityService,
              public productService: ProductService, public couponService: CouponService, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.invitePrize !== undefined && value.invitePrize.currentValue !== undefined) {
      this.setBuildFormValue(this.invitePrize);
    }
  }

  ngOnInit() {
    this.couponPageQuery.addOrFilter('couponType', 1, 'eq');
    this.couponPageQuery.addOrFilter('couponType', 2, 'eq');
    this.commodityPageQuery.addFilter('shelfState', 1, 'eq');
    this.commodityPageQuery.addOnlyFilter('auditState', 2, 'eq');
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      inviteActivity: [null, Validators.compose([Validators.required])],
      prizeNo: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      prizeName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      inviteNum: [null, Validators.compose([Validators.required, Validators.maxLength(3)])],
      prizeType: [1],
      integral: [null],
      product: [null],
      commodity: [null],
      coupon: [null],
      sort: [0, Validators.compose([Validators.required])],
      state: [
        0, Validators.compose([Validators.required,
          Validators.maxLength(3),
        ]),
      ],
    });

    this.commonForm.get('integral').setValidators(prizeTypeIntegralValidator(this.commonForm.get('prizeType')));
    this.commonForm.get('product').setValidators(prizeTypeProductValidator(this.commonForm.get('prizeType')));
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

  setBuildFormValue(invitePrize: InvitePrizeBo) {
    this.commonForm.setValue({
      inviteActivity:
      invitePrize.inviteActivity
      ,
      prizeNo:
      invitePrize.prizeNo
      ,
      prizeName:
      invitePrize.prizeName
      ,
      inviteNum:
      invitePrize.inviteNum
      ,
      prizeType:
      invitePrize.prizeType
      ,
      integral:
      invitePrize.integral
      ,
      commodity:
      invitePrize.commodity
      ,
      product:
      invitePrize.product
      ,
      coupon:
      invitePrize.coupon
      ,
      sort:
      invitePrize.sort
      ,
      state:
      invitePrize.state,
    });

    if(invitePrize.commodity){
      this.productPageQuery.addOnlyOneFilter('commodity.id', invitePrize.commodity.id, 'eq');
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
    if (this.invitePrize) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    console.log('commonForm value=' + JSON.stringify(formValue));
    this.onTransmitFormValue.emit({ obj: formValue });
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  setInviteActivity(event) {
    if (event.title != null && event.length != 0) {
      this.commonForm.patchValue({
        inviteActivity: event,
      });
    }
  }

  setCommodity(event) {
    if (event.commodityName != null && event.length != 0) {
      this.commonForm.patchValue({
        commodity: event,
      });

      this.productPageQuery.addOnlyOneFilter('commodity.id', event.id, 'eq');
      if(this.productModalSelect){
        this.productModalSelect.getData();
        this.productModalSelect.resultName = '请选择';
      }
      this.commonForm.patchValue({
        product: null,
      });
    }
  }

  setProduct(event) {
    if (event.productName != null && event.length != 0) {
      this.commonForm.patchValue({
        product: event,
      });
      // this.productModalSelect.select
      console.log('product value=' + JSON.stringify(this.commonForm.value.product));
    }
  }

  setCoupon(event) {
    if (event.couponName) {
      this.commonForm.patchValue({
        coupon: event,
      });
    }
  }

}
