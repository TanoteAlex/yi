import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Recommend} from '../../../models/original/recommend.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";

@Component({
  selector: 'form-recommend',
  templateUrl: './form-recommend.component.html',
  styleUrls: ['./form-recommend.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRecommendComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() recommend: Recommend =new Recommend();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        id:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        guid:[
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        title:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        imgPath:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        state:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        createTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        deleted:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        delTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService,public commodityService:CommodityService, public msg: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.recommend !== undefined && value.recommend.currentValue !== undefined) {
            this.setBuildFormValue(this.recommend);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({

            title: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(127)
                ])
            ],
            state: [
              0, Validators.compose([Validators.required,
                Validators.min(0), Validators.max(1)
              ])
            ],
          commodities: [[]],
        });
    }

    setBuildFormValue(recommend: Recommend) {
        this.commonForm.setValue({
          title:
          recommend.title
          ,
          commodities: recommend.commodities
          ,
          state:
          recommend.state
        });
          if (this.commonForm.value.commodities) {
            this.freightTemplateName="";
            this.commonForm.value.commodities.forEach((e, index) => {
              this.select=""
              if (this.commonForm.value.commodities.length != index + 1) {
                this.freightTemplateName += e.commodityName + ","
              } else {
                this.freightTemplateName += e.commodityName
              }
            })
          }
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
 if (this.recommend) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if(this.select == "请选择"){
          this.msg.success("推荐商品不能为空！");
          return;
        }
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack(){
        this.location.back();
    }

  freightTemplateName='请选择'
  select="请选择"
  setParentCategory(event) {
    if(event==""){
      this.select="请选择"
      this.freightTemplateName = "请选择";
    }else{
      this.select=""
      this.commonForm.patchValue({
        commodities: event.map(e => {
          this.select=event.name
          return {id: e.id, commodityName: e.commodityName, commodityNo: e.commodityNo , imgPath: e.imgPath}
        })
      });
    }
  }


/*
  setParentCategory(event) {
    console.log(event)
    this.commonForm.patchValue({
      commodities: event.map(e => {
        return {id: e.id, commodityName: e.commodityName}
      })
    });
  }
*/

}
