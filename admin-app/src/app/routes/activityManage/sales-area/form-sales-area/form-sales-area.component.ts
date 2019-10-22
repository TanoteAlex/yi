import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SalesAreaBo } from '../../../models/domain/bo/sales-area-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-sales-area',
  templateUrl: './form-sales-area.component.html',
  styleUrls: ['./form-sales-area.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FormSalesAreaComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() salesArea: SalesAreaBo = new SalesAreaBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {
    title: [
      {
        name: 'required',
        msg: '标题不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      },
    ],
    banner: [
      {
        name: 'required',
        msg: '请上传图片',
      },
    ],
    showMode: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.salesArea !== undefined && value.salesArea.currentValue !== undefined) {
      this.setBuildFormValue(this.salesArea);
    }
  }

  ngOnInit() {

  }

  getPic(event) {
    if (event == 'failure') {
      this.commonForm.patchValue({ banner: null });
    } else {
      this.commonForm.patchValue({ banner: event.data[0].url });
    }
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      title: [null, Validators.compose([Validators.required,
        Validators.maxLength(32),
      ])],
      banner: [null, Validators.required],
      showMode: [1, Validators.required],
      state: [0, Validators.required],
    });
  }

  setBuildFormValue(salesArea: SalesAreaBo) {
    this.commonForm.setValue({
      title:
      salesArea.title
      ,
      banner:
      salesArea.banner
      ,
      showMode:
      salesArea.showMode
      ,
      state:
      salesArea.state
      ,
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
    if (this.salesArea) {
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

}
