import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FreightTemplate} from '../../../models/original/freight-template.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {AreaService} from "../../../services/area.service";
import {RegionGroupComponent} from "../../../components/region-group/region-group.component";

@Component({
  selector: 'form-freight-template',
  templateUrl: './form-freight-template.component.html',
  styleUrls: ['./form-freight-template.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormFreightTemplateComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() freightTemplate: FreightTemplate = new FreightTemplate();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  @ViewChild('regionGroup') regionGroup: RegionGroupComponent

  formErrors = {

    templateName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
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
    presetWeight: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    presetFee: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    extraWeight: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    extraFee: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    regions: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ]

  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public areaService: AreaService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.freightTemplate !== undefined && value.freightTemplate.currentValue !== undefined) {
      this.setBuildFormValue(this.freightTemplate);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      templateName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      chargeMode: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(3)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      presetWeight: [null, Validators.compose([Validators.required, Validators.maxLength(15)])],
      presetFee: [null, Validators.compose([Validators.required, Validators.maxLength(15)])],
      extraWeight: [null, Validators.compose([Validators.required, Validators.maxLength(15)])],
      extraFee: [null, Validators.compose([Validators.required, Validators.maxLength(15)])],
      regions: [null, Validators.compose([Validators.required])],
    });
  }

  setBuildFormValue(freightTemplate: FreightTemplate) {
    this.commonForm.setValue({
      templateName: freightTemplate.templateName,
      chargeMode: freightTemplate.chargeMode,
      state: freightTemplate.state,
      presetWeight: freightTemplate.presetWeight,
      presetFee: freightTemplate.presetFee,
      extraWeight: freightTemplate.extraWeight,
      extraFee: freightTemplate.extraFee,
      regions: freightTemplate.regions,
    });
    this.regionGroup.select(freightTemplate.regions)
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

  setArea(event) {
    this.commonForm.patchValue({
      regions: event
    })
  }

}

