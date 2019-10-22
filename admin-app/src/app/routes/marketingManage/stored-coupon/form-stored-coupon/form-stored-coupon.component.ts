import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService, NzRangePickerComponent} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Coupon} from '../../../models/original/coupon.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";
import {MemberLevelService} from "../../../services/member-level.service";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";
import {PageQuery} from "../../../models/page-query.model";

@Component({
  selector: 'form-stored-coupon',
  templateUrl: './form-stored-coupon.component.html',
  styleUrls: ['./form-stored-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None,

})
export class FormStoredCouponComponent implements OnInit, OnChanges {

  commonForm: FormGroup;
  @Input() title: string = '表单';
  dateFormat = 'yyyy/MM/dd';

  @Input() coupon: Coupon = new Coupon();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('modalSelectCommodity') modalSelectCommodity: ModalSelecetComponent;
  @ViewChild('modalSelectMember') modalSelectMember: ModalSelecetComponent;
  commodityPageQuery: PageQuery = new PageQuery();

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
    limited: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    memberLevels: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService, public  memberLevelService: MemberLevelService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.coupon !== undefined && value.coupon.currentValue !== undefined) {
      this.setBuildFormValue(this.coupon);
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addOnlyOneFilter('auditState', 2, 'eq');
    this.commodityPageQuery.addOnlyOneFilter('shelfState', 1, 'eq');
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      couponName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      parValue: [null, Validators.compose([Validators.required, Validators.maxLength(15)])],
      quantity: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
      limited: [null],
      memberLevels: [null, Validators.compose([Validators.required])],
      couponType:[3],
    });
  }

  setBuildFormValue(coupon: Coupon) {
    this.commonForm.setValue({
      couponName: coupon.couponName,
      parValue: coupon.parValue,
      quantity: coupon.quantity,
      limited: coupon.limited,
      couponType: coupon.couponType,
      memberLevels: coupon.memberLevels,
    });
    //会员等级回调
    this.modalSelectMember.dataRetrieval(coupon.memberLevels)
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
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  setMemberLevel(event) {
    this.commonForm.patchValue({
      memberLevels: event.map(e => {
        return {id: e.id, name: e.name}
      })
    });
  }

}
