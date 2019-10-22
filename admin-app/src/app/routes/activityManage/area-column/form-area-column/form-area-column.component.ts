import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AreaColumnBo } from '../../../models/domain/bo/area-column-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { SalesAreaService } from '../../../services/sales-area.service';
import { ModalSelecetComponent } from '../../../components/modal-selecet/modal-selecet.component';

@Component({
  selector: 'form-area-column',
  templateUrl: './form-area-column.component.html',
  styleUrls: ['./form-area-column.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [SalesAreaService],
})
export class FormAreaColumnComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() areaColumn: AreaColumnBo = new AreaColumnBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('salesAreaModalSelect') salesAreaModalSelect: ModalSelecetComponent;

  formErrors = {
    salesArea: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    title: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      },
    ],
    banner: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    showMode: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    sort: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      },
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public salesAreaService: SalesAreaService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.areaColumn !== undefined && value.areaColumn.currentValue !== undefined) {
      this.setBuildFormValue(this.areaColumn);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      salesArea: [null, Validators.compose([Validators.required])],
      title: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      banner: [null, Validators.required],
      showMode: [1, Validators.required],
      sort: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
      state: [0, Validators.required],
    });
  }

  setBuildFormValue(areaColumn: AreaColumnBo) {
    this.commonForm.setValue({
      salesArea:
      areaColumn.salesArea
      ,
      title:
      areaColumn.title
      ,
      banner:
      areaColumn.banner
      ,
      showMode:
      areaColumn.showMode
      ,
      sort:
      areaColumn.sort
      ,
      state:
      areaColumn.state,
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
    if (this.areaColumn) {
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

  setSalesArea(event) {
    if (event.title != null && event.length != 0) {
      this.commonForm.patchValue({
        salesArea: event,
      });
    }
  }

  getPic(event) {
    if (event == 'failure') {
      this.commonForm.patchValue({ banner: null });
    } else {
      this.commonForm.patchValue({ banner: event.data[0].url });
    }
  }

}
