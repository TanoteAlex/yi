import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Comment} from '../../../models/original/comment.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";
import {MemberService} from "../../../services/member.service";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
  selector: 'form-comment',
  templateUrl: './form-comment.component.html',
  encapsulation: ViewEncapsulation.None
})
export class FormCommentComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() comment: Comment = new Comment();
  commodityPageQuery: PageQuery = new PageQuery();
  memberPageQuery: PageQuery = new PageQuery()
  @ViewChild('memberModalSelect') memberModalSelect: ModalSelecetComponent;
  @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  rate: number = 1;

  formErrors = {

    commodity: [
      {
        name: 'required',
        msg: '不可为空',
      }
    ],
    member: [
      {
        name: 'required',
        msg: '不可为空',
      }
    ],
    commentStar: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    commentContent: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大256位长度',
      }
    ],
    display: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    imgPath: [
      {
        name: '',
        msg: '',
      }
    ],
    attachmentVos: [],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService, public memberService: MemberService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.comment !== undefined && value.comment.currentValue !== undefined) {
      this.setBuildFormValue(this.comment);
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addOnlyOneFilter("shelfState", 1, "eq");
    this.commodityPageQuery.addOnlyOneFilter("auditState", 2, "eq");
    this.memberPageQuery.addOnlyFilter("state", 0, "eq");
  }

  changeStart(i) {
    this.commonForm.patchValue({
      commentStar: i
    })
  }

  setCommodityComment(event) {
    if (event.commodityName) {
      this.commonForm.patchValue({
        commodity: {
          id: event.id,
          commodityShortName: event.commodityShortName,
        }
      });
    }
  }

  setMemberComment(event) {
    if (event.username) {
      this.commonForm.patchValue({
        member: {
          id: event.id,
          username: event.username,
        }
      });
    }
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      commodity: [null, Validators.compose([Validators.required])],
      member: [null, Validators.compose([Validators.required])],
      commentStar: [null, Validators.compose([Validators.required])],
      commentContent: [null, Validators.compose([Validators.required, Validators.maxLength(256)])],
      display: [
        1, Validators.compose([Validators.required,
          Validators.min(0), Validators.max(1)
        ])
      ],
      imgPath: [null],
      attachmentVos: [],
    });
  }

  imgPaths = [];

  attachmentVoss = [];

  setBuildFormValue(comment: Comment) {
    this.commonForm.setValue({
      commodity: {
        id: comment.commodity.id,
        commodityName: comment.commodity.commodityName,
      },
      member: {
        id: comment.member.id,
        username: comment.member.username,
      },
      commentStar: comment.commentStar,
      commentContent: comment.commentContent,
      display: comment.display,
      imgPath: comment.imgPath,
      attachmentVos: comment.attachmentVos,
    });
    this.commonForm.value.attachmentVos.forEach(e => {
      this.imgPaths.push({url: e.filePath})
    })
    this.attachmentVoss = this.commonForm.value.attachmentVos;
    //设置商品
    this.commodityModalSelect.dataRetrieval(comment.commodity)
    //设置会员
    this.memberModalSelect.dataRetrieval(comment.member)
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

}
