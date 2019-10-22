import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ArticleComment} from '../../../models/original/article-comment.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {ArticleService} from "../../../services/article.service";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
  selector: 'form-article-comment',
  templateUrl: './form-article-comment.component.html',
  styleUrls: ['./form-article-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormArticleCommentComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() articleComment: ArticleComment = new ArticleComment();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  articlePageQuery: PageQuery = new PageQuery();
  @ViewChild('articleModalSelect') articleModalSelect: ModalSelecetComponent;

  imgPaths = [];

  formErrors = {
    article: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    commentator: [
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
    commentContent: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大255位长度',
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
    attachmentVos: [],

  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public articleService: ArticleService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.articleComment !== undefined && value.articleComment.currentValue !== undefined) {
      this.setBuildFormValue(this.articleComment);
    }
  }


  /**
   * 赋值商品图片数组
   */
  getCommodityImgPath() {
    if (this.commonForm.value.attachmentVos != null) {
      this.imgPaths = []
      this.commonForm.value.attachmentVos.forEach(e => {
        if (e != null) {
          this.imgPaths.push({oldValue: e, url: e.url ? e.url : e.filePath, status: 'edit'})
        }
      })
    }
    if (this.imgPaths.length == 0) {
      this.commonForm.patchValue({
        imgPath: null
      })
    }
  }

  /**
   * 商品图片回调
   * @param fileList
   */
  changeAttachment(fileList) {
    let newAttachmentVos = fileList.map(e => {
      if (e.status == 'edit') {
        return e.oldValue
      } else {
        return e.response ? e.response.data[0] : null
      }
    })
    this.commonForm.patchValue({
      attachmentVos: newAttachmentVos
    })
    if (newAttachmentVos.length != 0) {
      this.commonForm.patchValue({
        imgPath: newAttachmentVos[0].url ? newAttachmentVos[0].url : newAttachmentVos[0].filePath
      })
    }
    this.getCommodityImgPath()
  }


  ngOnInit() {
    this.articlePageQuery.addOnlyOneFilter("state", 0, "eq");
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      article: [null, Validators.compose([Validators.required])],
      commentator: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
      imgPath: [null, Validators.compose([Validators.required])],
      commentContent: [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      attachmentVos: [],
    });
  }

  setBuildFormValue(articleComment: ArticleComment) {
    this.commonForm.setValue({
      article: {
        id: articleComment.article.id,
        title: articleComment.article.title,
      },
      imgPath: articleComment.imgPath,
      commentator: articleComment.commentator,
      commentContent: articleComment.commentContent,
      state: articleComment.state,
      attachmentVos: articleComment.attachmentVos,
    });
    //赋值商品图片数组
    this.getCommodityImgPath();
    //设置爱生活
    this.articleModalSelect.dataRetrieval(articleComment.article)
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


  setArticle(e) {
    if (e.title) {
      this.commonForm.patchValue({
        article: {
          id: e.id,
          title: e.title
        }
      })
    }
  }

}
