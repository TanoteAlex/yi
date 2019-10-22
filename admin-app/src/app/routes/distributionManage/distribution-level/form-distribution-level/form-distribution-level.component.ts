import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DistributionLevelBo} from '../../../models/domain/bo/distribution-level-bo.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-distribution-level',
  templateUrl: './form-distribution-level.component.html',
  styleUrls: ['./form-distribution-level.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormDistributionLevelComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() distributionLevel: DistributionLevelBo = new DistributionLevelBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    name: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    rank: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    commissionRate: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大5位长度',
      }
    ],
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.distributionLevel !== undefined && value.distributionLevel.currentValue !== undefined) {
      this.setBuildFormValue(this.distributionLevel);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      name: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      rank: [],
      commissionRate: [null, Validators.compose([Validators.required, Validators.maxLength(5)])],
      remark: [null, Validators.compose([Validators.maxLength(127)])],
    });
  }

  setBuildFormValue(distributionLevel: DistributionLevelBo) {
    this.commonForm.setValue({
      name: distributionLevel.name,
      rank: distributionLevel.rank,
      commissionRate: distributionLevel.commissionRate,
      remark: distributionLevel.remark,
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
    if (this.distributionLevel) {
    }
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

}
