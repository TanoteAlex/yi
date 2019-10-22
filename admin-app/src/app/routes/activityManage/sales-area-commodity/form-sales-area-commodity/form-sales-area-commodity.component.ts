import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SalesAreaCommodityBo } from '../../../models/domain/bo/sales-area-commodity-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { PageQuery } from '../../../models/page-query.model';
import { ModalSelecetComponent } from '../../../components/modal-selecet/modal-selecet.component';
import { SalesAreaService } from '../../../services/sales-area.service';
import { CommodityService } from '../../../services/commodity.service';

@Component({
  selector: 'form-sales-area-commodity',
  templateUrl: './form-sales-area-commodity.component.html',
  styleUrls: ['./form-sales-area-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [SalesAreaService, CommodityService],
})
export class FormSalesAreaCommodityComponent implements OnInit, OnChanges {

  commonForm: FormGroup;
  //
  // salesAreaPageQuery: PageQuery = new PageQuery();
  //
  commodityPageQuery: PageQuery = new PageQuery();


  @Input() title: string = '表单';

  @Input() salesAreaCommodity: SalesAreaCommodityBo = new SalesAreaCommodityBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('salesAreaModalSelect') salesAreaModalSelect: ModalSelecetComponent;

  @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;

  formErrors = {

    salesArea: [
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

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService,
              public salesAreaService: SalesAreaService, public commodityService: CommodityService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.salesAreaCommodity !== undefined && value.salesAreaCommodity.currentValue !== undefined) {
      this.setBuildFormValue(this.salesAreaCommodity);
      this.originalId = this.salesAreaCommodity.commodity.id;
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addFilter('shelfState', 1, 'eq');
    this.commodityPageQuery.addOnlyFilter('auditState', 2, 'eq');
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      salesAreaCommodityId: [null],
      salesArea: [null, Validators.required],
      commodity: [null, Validators.required],
      sort: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
    });
  }

  setBuildFormValue(salesAreaCommodity: SalesAreaCommodityBo) {
    this.commonForm.setValue({
      salesAreaCommodityId: salesAreaCommodity.salesAreaCommodityId,
      salesArea: salesAreaCommodity.salesArea,
      commodity: salesAreaCommodity.commodity,
      sort: salesAreaCommodity.sort,
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
    if (this.salesAreaCommodity) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    formValue.salesAreaCommodityId = { salesAreaId: formValue.salesArea.id, commodityId: this.originalId == 0 ? formValue.commodity.id : this.originalId };
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

  setCommodity(event) {
    if (event.commodityName != null && event.length != 0) {
      this.commonForm.patchValue({
        commodity: event,
      });
    }
  }

}
