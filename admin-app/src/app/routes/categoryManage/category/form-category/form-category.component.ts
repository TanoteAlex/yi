import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Category} from '../../../models/original/category.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CategoryService} from "../../../services/category.service";
import {PageQuery} from "../../../models/page-query.model";
import {CategoryTreeSelectComponent} from "../../../components/category-tree-select/category-tree-select.component";

@Component({
  selector: 'form-category',
  templateUrl: './form-category.component.html',
  styleUrls: ['./form-category.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormCategoryComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() category: Category = new Category();
  pageQuery: PageQuery = new PageQuery();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  @ViewChild('categoryTreeSelect') categoryTreeSelect: CategoryTreeSelectComponent
  isShowPreCategory = true;

  formErrors = {
    parent: [],
    categoryName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    sort: [],
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public categoryService: CategoryService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.category !== undefined && value.category.currentValue !== undefined) {
      this.setBuildFormValue(this.category);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      parent: [],
      categoryName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      sort: [],
      remark: [null, Validators.compose([Validators.maxLength(127)])],
    });
  }


  setParent(event) {
    this.commonForm.patchValue({
      parent: {
        id: event.id,
        categoryName: event.categoryName,
      }
    });
  }

  setBuildFormValue(category: Category) {
    if (category.parent == null) {
      this.isShowPreCategory = false;
    } else {
      this.categoryTreeSelect.select(category.parent.id)
    }
    this.commonForm.setValue({
      parent: {
        id: category.parent ? category.parent.id : null,
        categoryName: category.parent ? category.parent.categoryName : null,
      },
      categoryName: category.categoryName,
      sort: category.sort,
      remark: category.remark,
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
    if (this.category) {
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
