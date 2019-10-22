import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExpressTemplate} from '../../../models/original/express-template.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-express-template',
  templateUrl: './form-express-template.component.html',
  styleUrls: ['./form-express-template.component.less'],
  encapsulation: ViewEncapsulation.None
})
export class FormExpressTemplateComponent implements OnInit, OnChanges {
  arryControl: FormArray;
  commonForm: FormGroup;
  commonForm1: FormGroup;

  @Input() title: string = '表单';

  @Input() expressTemplate: ExpressTemplate = new ExpressTemplate();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    id: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    guid: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
/*    templateNo: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],*/
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
    printWidth: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    printHigh: [
      {
        name: 'maxlength',
        msg: '最大15位长度',
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
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.expressTemplate !== undefined && value.expressTemplate.currentValue !== undefined) {
      this.setBuildFormValue(this.expressTemplate);
    }
  }

  ngOnInit() {

  }

  deleteName(i) {
    let arryControl: FormArray = this.commonForm1.get('arryControl') as FormArray;
    arryControl.removeAt(i)
  }


  addNewTemplate() {
    let arryControl: FormArray = this.commonForm1.get('arryControl') as FormArray;
    for (let i = 0; i < arryControl.length; i++) {
/*      if (arryControl.at(i).value.templateNo == null || arryControl.at(i).value.templateNo == "") {
        this.msgSrv.warning('请输入模板' + (i + 1) + '单据编号！');
        return;
      }*/
      if (arryControl.at(i).value.templateName == null || arryControl.at(i).value.templateName == "") {
        this.msgSrv.warning('请输入模板' + (i + 1) + '单据名称！');
        return;
      }
      if (arryControl.at(i).value.printWidth == null || arryControl.at(i).value.printWidth == "") {
        this.msgSrv.warning('请输入模板' + (i + 1) + '打印宽！');
        return;
      }
      if (arryControl.at(i).value.printHigh == null || arryControl.at(i).value.printHigh == "") {
        this.msgSrv.warning('请输入模板' + (i + 1) + '打印高！');
        return;
      }
    }

    arryControl.push(this.fb.group({
      // templateNo: [],
      templateName: [],
      printWidth: [],
      printHigh: [],
      state: [0],
    }))
  }

  buildForm(): void {
    this.commonForm1 = this.fb.group({
      arryControl: this.fb.array([this.fb.group({
        // templateNo: [
        //   null, Validators.compose([Validators.required,
        //     Validators.maxLength(32)
        //   ])
        // ],
        templateName: [
          null, Validators.compose([Validators.required,
            Validators.maxLength(32)
          ])
        ],
        printWidth: [
          null, Validators.compose([Validators.required,
            Validators.maxLength(15)
          ])
        ],
        printHigh: [
          null, Validators.compose([Validators.required,
            Validators.maxLength(15)
          ])
        ],
        state: [
          0, Validators.compose([Validators.required,
            Validators.min(0), Validators.max(1)
          ])
        ],
      })])
    })
    this.arryControl = this.commonForm1.get('arryControl') as FormArray
  }

  setBuildFormValue(expressTemplate: ExpressTemplate) {
    this.commonForm1.patchValue({
      arryControl:  [{
       // templateNo:
       // expressTemplate.templateNo
       // ,
       templateName:
       expressTemplate.templateName
       ,
       printWidth:
       expressTemplate.printWidth
       ,
       printHigh:
       expressTemplate.printHigh
       ,
       state:
       expressTemplate.state
       ,
     }],

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
    const formValue = this.commonForm1.value.arryControl;
    for (let i = 0; i < formValue.length; i++) {
      // if (formValue[i].templateNo == null || formValue[i].templateNo == "") {
      //   this.msgSrv.warning('请输入模板' + (i + 1) + '单据编号！');
      //   return;
      // }
      if (formValue[i].templateName == null || formValue[i].templateName == "") {
        this.msgSrv.warning('请输入模板' + (i + 1) + '单据名称！');
        return;
      }
      if (formValue[i].printWidth == null || formValue[i].printWidth == "") {
        this.msgSrv.warning('请输入模板' + (i + 1) + '打印宽！');
        return;
      }
      if (formValue[i].printHigh == null || formValue[i].printHigh == "") {
        this.msgSrv.warning('请输入模板' + (i + 1) + '打印高！');
        return;
      }
    }

    if (this.expressTemplate) {
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
