import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegionGroup} from '../../../models/original/region-group.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {AreaService} from "../../../services/area.service";
import {RegionTreeSelectComponent} from "../../../components/region-tree-select/region-tree-select.component";

@Component({
  selector: 'form-region-group',
  templateUrl: './form-region-group.component.html',
  styleUrls: ['./form-region-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRegionGroupComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() regionGroup: RegionGroup = new RegionGroup();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('regionTreeSelect') regionTreeSelect: RegionTreeSelectComponent;

  formErrors = {
    name: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    sort: [],
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
    regions: [
      {
        name: 'required',
        msg: '不可为空',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public areaService: AreaService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.regionGroup !== undefined && value.regionGroup.currentValue !== undefined) {
      this.setBuildFormValue(this.regionGroup);
    }
  }

  ngOnInit() {

  }

  setRegion(event) {
    this.commonForm.patchValue({
      regions: event.map(e => {
        return {area: e}
      })
    })
  }

  setCommodityOperateCategory(choose) {
    this.commonForm.patchValue({
      operateCategories: choose
    })
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      name: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
      sort: [],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      regions: [null, Validators.compose([Validators.required])],
    });
  }

  setBuildFormValue(regionGroup: RegionGroup) {
    this.regionTreeSelect.showSelect(regionGroup.regions)
    this.commonForm.setValue({
      name: regionGroup.name,
      sort: regionGroup.sort,
      state: regionGroup.state,
      regions: regionGroup.regions.map(e => {
        return {area: e.area}
      })
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
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    // console.log("commonForm value=" + JSON.stringify(formValue));
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

}
