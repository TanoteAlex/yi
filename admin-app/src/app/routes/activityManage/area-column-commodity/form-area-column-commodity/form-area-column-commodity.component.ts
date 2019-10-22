import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AreaColumnCommodityBo } from '../../../models/domain/bo/area-column-commodity-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { AreaColumnService } from '../../../services/area-column.service';
import { CommodityService } from '../../../services/commodity.service';
import { PageQuery } from '../../../models/page-query.model';

@Component({
  selector: 'form-area-column-commodity',
  templateUrl: './form-area-column-commodity.component.html',
  styleUrls: ['./form-area-column-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [AreaColumnService, CommodityService],
})
export class FormAreaColumnCommodityComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  commodityPageQuery: PageQuery = new PageQuery();

  @Input() title: string = '表单';

  @Input() areaColumnCommodity: AreaColumnCommodityBo = new AreaColumnCommodityBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {
    areaColumn: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    commodity: [
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
  };

  originalId: number = 0;

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public areaColumnService: AreaColumnService,
              public commodityService: CommodityService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.areaColumnCommodity !== undefined && value.areaColumnCommodity.currentValue !== undefined) {
      this.setBuildFormValue(this.areaColumnCommodity);
      this.originalId = this.areaColumnCommodity.commodity.id;
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addFilter('shelfState', 1, 'eq');
    this.commodityPageQuery.addOnlyFilter('auditState', 2, 'eq');
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      areaColumnCommodityId: [null],
      areaColumn: [null, Validators.required],
      commodity: [null, Validators.required],
      sort: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
    });
  }

  setBuildFormValue(areaColumnCommodity: AreaColumnCommodityBo) {
    this.commonForm.setValue({
      areaColumnCommodityId: areaColumnCommodity.areaColumnCommodityId,
      areaColumn: areaColumnCommodity.areaColumn,
      commodity: areaColumnCommodity.commodity,
      sort: areaColumnCommodity.sort,
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
    if (this.areaColumnCommodity) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    formValue.areaColumnCommodityId = { areaColumnId: formValue.areaColumn.id, commodityId: this.originalId == 0 ? formValue.commodity.id : this.originalId };
    console.log('commonForm value=' + JSON.stringify(formValue));
    this.onTransmitFormValue.emit({ obj: formValue });
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  setAreaColumn(event) {
    if (event.title != null && event.length != 0) {
      this.commonForm.patchValue({
        areaColumn: event,
      });
    }
  }

  setCommodity(event) {
    if (event.commodityName != null && event.length != 0) {
      this.commonForm.patchValue({
        commodity: event,
      });
    }
  }

}
