import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {FreightTemplateConfig} from "../../../models/original/freight-template-config.model";

@Component({
  selector: 'form-freight-template-config',
  templateUrl: './form-freight-template-config.component.html',
  styleUrls: ['./form-freight-template-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormFreightTemplateConfigComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() freightTemplateConfig: FreightTemplateConfig = new FreightTemplateConfig();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    configName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    freightType: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    chargeMode: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    deliveryMode: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    freeCondition: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大0位长度',
      }
    ],
    deliveryTime: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ]
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.freightTemplateConfig !== undefined && value.freightTemplateConfig.currentValue !== undefined) {
      this.setBuildFormValue(this.freightTemplateConfig);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      configName: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
      freightType: [1, Validators.compose([Validators.required, Validators.maxLength(3)])],
      chargeMode: [1, Validators.compose([Validators.required, Validators.maxLength(3)])],
      deliveryMode: [1, Validators.compose([Validators.required, Validators.maxLength(3)])],
      freeCondition: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
      state: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
      deliveryTime: [null, Validators.compose([Validators.required])],
      timeUnit: [null],
      customFreightTemplates: this.fb.array([this.fb.group({
        id: 0,
        defaulted: 1,
        deliveryMode: 1,
        firstQuantity: 1,
        firstFee: 0,
        extraQuantity: 1,
        extraFee: 0,
        freightTemplateConfig: {id: 0},
        regions: [[]]
      })]),
      freeFreightTemplates: this.fb.array([this.fb.group({
        id: 0,
        defaulted: 1,
        deliveryMode: 1,
        freeType: 1,
        fullQuantity: 0,
        fullAmount: 0,
        freightTemplateConfig: {id: 0},
        regions: [[]]
      })]),
    });
  }


  setBuildFormValue(freightTemplateConfig: FreightTemplateConfig) {
    this.commonForm.patchValue({
      configName: freightTemplateConfig.configName,
      freightType: freightTemplateConfig.freightType,
      chargeMode: freightTemplateConfig.chargeMode,
      deliveryMode: freightTemplateConfig.deliveryMode,
      freeCondition: freightTemplateConfig.freeCondition,
      state: freightTemplateConfig.state,
      deliveryTime: freightTemplateConfig.deliveryTime,
      timeUnit: this.freightTemplateConfig.timeUnit,
    });
    if (freightTemplateConfig.customFreightTemplates.length != 0) {
      this.commonForm.setControl('customFreightTemplates',
        this.fb.array(freightTemplateConfig.customFreightTemplates.map(e => {
          let formGroup = this.fb.group({
            id: e.id,
            defaulted: e.defaulted,
            deliveryMode: e.deliveryMode,
            firstQuantity: e.firstQuantity,
            firstFee: e.firstFee,
            extraQuantity: e.extraQuantity,
            extraFee: e.extraFee,
            freightTemplateConfig: {id: this.freightTemplateConfig.id},
            regions: []
          });
          formGroup.patchValue({
            regions: e.regions
          })
          return formGroup
        })))
    }
    if (freightTemplateConfig.freeFreightTemplates.length != 0) {
      this.commonForm.setControl('freeFreightTemplates',
        this.fb.array(freightTemplateConfig.freeFreightTemplates.map(e => {
          let formGroup = this.fb.group({
            id: e.id,
            defaulted: e.defaulted,
            deliveryMode: e.deliveryMode,
            freeType: e.freeType,
            fullQuantity: e.fullQuantity,
            fullAmount: e.fullAmount,
            freightTemplateConfig: {id: this.freightTemplateConfig.id},
            regions: []
          });
          formGroup.patchValue({
            regions: e.regions
          })
          return formGroup
        })))
    } else {
      this.commonForm.setControl('freeFreightTemplates', this.fb.array([]))
      this._addCustomFreight('freeFreightTemplates')
    }

  }

  checkedValues = [];

  setArea(event, item) {
    item.patchValue({
      regions: event
    })
    this.checkedValues = []
    this.commonForm.value.customFreightTemplates.forEach(e => {
      if (e.regions.length != 0) {
        e.regions.forEach(e1 => {
          this.checkedValues.push(e1.area)
        })
      }
    });
  }


  private _remove(i, formCtrlName) {
    let customFreightTemplates = this.commonForm.get(formCtrlName) as FormArray;
    customFreightTemplates.removeAt(i)
  }

  private _addCustomFreight(formCtrlName) {
    let customFreightTemplates = this.commonForm.get(formCtrlName) as FormArray;

    customFreightTemplates.push(formCtrlName == 'freeFreightTemplates' ?
      this.fb.group({
        id: 0,
        deliveryMode: 1,
        freeType: 1,
        fullQuantity: 0,
        fullAmount: 1,
        freightTemplateConfig: {id: this.freightTemplateConfig ? this.freightTemplateConfig.id : 0},
        regions: [[]]
      })
      :
      this.fb.group({
        id: 0,
        defaulted: 0,
        deliveryMode: 1,
        firstQuantity: 1,
        firstFee: 0,
        extraQuantity: 1,
        extraFee: 0,
        freightTemplateConfig: {id: this.freightTemplateConfig ? this.freightTemplateConfig.id : 0},
        regions: [[]]
      }))
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
    formValue.timeUnit = formValue.deliveryTime == 1 ? 1 : 2
    formValue.freeCondition = formValue.freeCondition ? 1 : 0
    // console.log("commonForm value=" + JSON.stringify(formValue));
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  deliveryTimes = [
    {name: "12小时内", value: 1},
    {name: "1天内", value: 2},
    {name: "3天内", value: 3},
    {name: "5天内", value: 4},
  ]
}
