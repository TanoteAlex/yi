import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Comment} from '../../../models/original/comment.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-comment',
  templateUrl: './form-comment.component.html',
  encapsulation: ViewEncapsulation.None
})
export class FormCommentComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() comment: Comment =new Comment();

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
        commodityId:[
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        commodityName:[
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        memberId:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        nickname:[
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        serialNo:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        commentStar:[
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        commentContent:[
            {
                name: 'maxlength',
                msg: '最大256位长度',
            }
        ],
        replyContent:[
            {
                name: 'maxlength',
                msg: '最大256位长度',
            }
        ],
        display:[
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        commentTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        replyTime:[
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
                msg: '最大3位长度',
            }
        ],
        delTime:[
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.comment !== undefined && value.comment.currentValue !== undefined) {
            this.setBuildFormValue(this.comment);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
            ],
            commodityId: [
            ],
            commodityName: [
            ],
            memberId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            nickname: [
            ],
            serialNo: [
            ],
            commentStar: [
            ],
            commentContent: [
            ],
            replyContent: [
            ],
            display: [
            ],
            commentTime: [
            ],
            replyTime: [
            ],
        });
    }

    setBuildFormValue(comment: Comment) {
        this.commonForm.setValue({
            guid:
            comment.guid
                ,
            commodity:
            comment.commodity
                ,
            commodityName:
            comment.commodityName
                ,
            member:
            comment.member
                ,
            nickname:
            comment.nickname
                ,
            serialNo:
            comment.serialNo
                ,
            commentStar:
            comment.commentStar
                ,
            commentContent:
            comment.commentContent
                ,
            replyContent:
            comment.replyContent
                ,
            display:
            comment.display
                ,
            commentTime:
            comment.commentTime
                ,
            replyTime:
            comment.replyTime
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
 if (this.comment) {
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

}
