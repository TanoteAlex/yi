import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Article} from '../../../models/original/article.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {EditorComponent} from "../../../components/editor/editor.component";
import {CommodityService} from "../../../services/commodity.service";

@Component({
  selector: 'form-article',
  templateUrl: './form-article.component.html',
  styleUrls: ['./form-article.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormArticleComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() article: Article =new Article();

    @ViewChild('editor') editor: EditorComponent;

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

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
        subtitle:[
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        author:[
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        content:[
            {
                name: 'maxlength',
                msg: '最大65535位长度',
            }
        ],
        imgPath:[
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        url:[
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
   /*     articleType:[
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],*/
        state:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
      commodities:[]
    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.article !== undefined && value.article.currentValue !== undefined) {
            this.setBuildFormValue(this.article);
        }
    }

    getPic(event){
    }

    getPic1(event){
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
            subtitle: [
            ],
            author: [
            ],
            content: [
            ],
            imgPath: [
            ],
            url: [
            ],
         /*   articleType: [
            ],*/
            state: [
                0, Validators.compose([Validators.required,
                Validators.min(0), Validators.max(1)
                ])
            ],
          commodities:[[]]
        });
    }
 /* thematicText(event) {
    this.commonForm.patchValue({
      description: event
    });
  }*/


  descriptionContent="";
 selectCommodityName=""
    setBuildFormValue(article: Article) {
        this.commonForm.setValue({
            title:
            article.title
                ,
            subtitle:
            article.subtitle
                ,
            author:
            article.author
                ,
            content:
            article.content
                ,
            imgPath:
            article.imgPath
                ,
            url:
            article.url
                ,
          /*  articleType:
            article.articleType
                ,*/
            state:
            article.state
                ,
          commodities:
          article.commodities
        });

      this.descriptionContent = this.commonForm.value.content;

      if (this.commonForm.value.commodities) {
        this.selectCommodityName=""
        this.commonForm.value.commodities.forEach((e, index) => {
          if (this.commonForm.value.commodities.length != index + 1) {
            this.selectCommodityName += e.commodityName + ","
          } else {
            this.selectCommodityName += e.commodityName
          }
        })
      }
    }



  thematicText(event) {
    this.commonForm.patchValue({
      content: event
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
 if (this.article) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack(){
        this.location.back();
    }

  setCommoditySupplier(event) {
    this.commonForm.patchValue({
      commodities: event.map(e => {
        return {id: e.id, commodityName: e.commodityName, commodityNo: e.commodityNo , imgPath: e.imgPath}
      })
    });
  }

}
