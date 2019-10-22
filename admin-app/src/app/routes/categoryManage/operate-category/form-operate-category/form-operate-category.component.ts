import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {PageQuery} from "../../../models/page-query.model";
import {OperateCategory} from "../../../models/original/operate-category.model";
import {OperateCategoryService} from "../../../services/operate-category.service";
import {CategoryTreeSelectComponent} from "../../../components/category-tree-select/category-tree-select.component";
import {SUCCESS} from "../../../models/constants.model";
import {CommodityService} from "../../../services/commodity.service";
import {ArticleService} from "../../../services/article.service";
import {articleValidator, commodityValidator} from "@shared/custom-validators/custom-validator";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
  selector: 'form-operate-category',
  templateUrl: './form-operate-category.component.html',
  styleUrls: ['./form-operate-category.component.less'],
  encapsulation: ViewEncapsulation.None
})
export class FormOperateCategoryComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() operateCategory: OperateCategory = new OperateCategory();
  commodityPageQuery: PageQuery = new PageQuery();
  articlePageQuery: PageQuery = new PageQuery();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  @ViewChild('categoryTreeSelect') categoryTreeSelect: CategoryTreeSelectComponent
  @ViewChild('modalSelect') modalSelect: ModalSelecetComponent;
  @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;
  @ViewChild('articleModalSelect') articleModalSelect: ModalSelecetComponent;
  isShowPreCategory = true;

  linkTypes = [
    {code: 1, title: "商品",},
    {code: 2, title: "文章",},
    // {code: 3, title: "活动",},
    // {code: 4, title: "专区",}
    ]

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
    imgPath: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    state: [],
    sort: [],
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    linkType: [],
    linkId: [],
    showName: [],
    commodity: [
      {
        name: 'commodityRequire',
        msg: '请选择商品'
      }
    ],
    article: [
      {
        name: 'articleRequire',
        msg: '请选择文章'
      }
    ],
    commodities:[[]],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public operateCategoryService: OperateCategoryService, public commodityService: CommodityService, public articleService: ArticleService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.operateCategory !== undefined && value.operateCategory.currentValue !== undefined) {
      this.setBuildFormValue(this.operateCategory);
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addFilter("shelfState", 1, "eq");
    this.commodityPageQuery.addOnlyFilter("auditState", "2", "eq")
    this.articlePageQuery.addOnlyFilter("state", "0", "eq")
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      parent: [],
      categoryName: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      imgPath: [null, Validators.compose([Validators.maxLength(127)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      sort: [],
      remark: [null, Validators.compose([Validators.maxLength(127)])],
      linkType: [],
      linkId: [],
      showName: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      commodity: [null],
      article: [null],
      commodities: [[]],
    });
    //自定义图片链接表单
    this.commonForm.get('commodity').setValidators(commodityValidator(this.commonForm.get('linkType')))
    this.commonForm.get('article').setValidators(articleValidator(this.commonForm.get('linkType')))
  }

  setParent(event) {
    this.commonForm.patchValue({
      parent: {
        id: event.id,
        categoryName: event.categoryName,
      }
    });
  }

  getPic1(event) {
    this.commonForm.patchValue({
      imgPath: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
    })
  }

  setBuildFormValue(operateCategory: OperateCategory) {
    if (operateCategory.parent == null) {
      this.isShowPreCategory = false;
    } else {
      this.categoryTreeSelect.select(operateCategory.parent.id)
    }
    this.commonForm.setValue({
      parent: {
        id: operateCategory.parent ? operateCategory.parent.id : null,
        categoryName: operateCategory.parent ? operateCategory.parent.categoryName : null,
      },
      categoryName: operateCategory.categoryName,
      imgPath: operateCategory.imgPath,
      state: operateCategory.state,
      sort: operateCategory.sort,
      remark: operateCategory.remark,
      showName: operateCategory.showName,
      linkType: operateCategory.linkType,
      linkId: operateCategory.linkId,
      commodity: [],
      article: [],
      commodities: operateCategory.commodities,
    });

    this.modalSelect.dataRetrieval(operateCategory.commodities)
    if (this.operateCategory != null) {
      if (this.operateCategory.linkType == 1) {
        this.commodityService.getVoById(this.operateCategory.linkId).subscribe(response => {
          if (response.result == SUCCESS) {
            this.commonForm.patchValue({
              commodity: {
                id: response.data.id,
                commodityShortName: response.data.commodityShortName
              },
              article: []
            })
            //设置商品
            this.commodityModalSelect.dataRetrieval(this.commonForm.value.commodity)
          } else {
            this.msgSrv.error('请求失败', response.message);
          }
        }, error => {
          this.msgSrv.error('请求错误', error.message);
        });
      } else if (this.operateCategory.linkType == 2) {
        this.articleService.getVoById(this.operateCategory.linkId).subscribe(response => {
          if (response.result == SUCCESS) {
            /*  this.article = response.data;*/
            this.commonForm.patchValue({
              article: {
                id: response.data.id,
                title: response.data.title
              },
              commodity: [],
            })
            //设置爱生活
            this.articleModalSelect.dataRetrieval(this.commonForm.value.article)
          } else {
            this.msgSrv.error('请求失败', response.message);
          }
        }, error => {
          this.msgSrv.error('请求错误', error.message);
        });
      }
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
    if (this.operateCategory) {
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

  setCommodity(event) {
    if(event.commodityName && event.length != 0){
      this.commonForm.patchValue({
        linkId: event.id
      })
      this.commonForm.patchValue({
        commodity: {
          id: event.id,
          commodityShortName: event.commodityShortName
        }
      })
      this.commonForm.patchValue({
        article: null
      })
    }
  }

  setArticle(event) {
    this.commonForm.patchValue({
      linkId: event.id
    })
    this.commonForm.patchValue({
      article: {
        id: event.id,
        title: event.title
      }
    })
    this.commonForm.patchValue({
      commodity: null
    })
  }

  setCommodities(event) {
    this.commonForm.patchValue({
      commodities: event.map(e => {
        return {id: e.id, commodityShortName: e.commodityShortName, commodityNo: e.commodityNo, imgPath: e.imgPath}
      })
    });
  }

}
