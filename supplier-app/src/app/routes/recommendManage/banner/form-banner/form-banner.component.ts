import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Banner} from '../../../models/original/banner.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-banner',
  templateUrl: './form-banner.component.html',
  styleUrls: ['./form-banner.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormBannerComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() banner: Banner = new Banner();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    title: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    imgPath: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    url: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    sort: [
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
        name: 'min',
        msg: '最小值为0',
      },
      {
        name: 'max',
        msg: '最大值为127',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.banner !== undefined && value.banner.currentValue !== undefined) {
      this.setBuildFormValue(this.banner);
    }
  }

  ngOnInit() {

  }

  imgPathPic = "";

  getPic(event) {
    this.imgPathPic = event.data[0].url;
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      title: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(127)
        ])
      ],
      imgPath: [null],
      url: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(255)
        ])
      ],
      sort: [
        null, Validators.compose([Validators.required,
          Validators.min(0),Validators.max(127)
        ])
      ],
      state: [
        0, Validators.compose([Validators.required,
          Validators.min(0), Validators.max(1)
        ])
      ],
    });
  }

  setBuildFormValue(banner: Banner) {
    this.commonForm.setValue({
      title:
      banner.title
      ,
      imgPath:
      banner.imgPath
      ,
      url:
      banner.url
      ,
      sort:
      banner.sort
      ,
      state:
      banner.state
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
    if(this.imgPathPic!=""){
      formValue.imgPath=this.imgPathPic;
    }
    if (this.banner) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }

    if(!this.commonForm.value.imgPath){
      this.msgSrv.warning('请添加商品图片！');
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
