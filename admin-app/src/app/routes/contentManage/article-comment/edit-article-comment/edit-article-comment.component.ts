
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { ArticleCommentService } from '../../../services/article-comment.service';
import { ArticleComment } from '../../../models/original/article-comment.model';

@Component({
  selector: 'edit-article-comment',
  templateUrl: './edit-article-comment.component.html',
  styleUrls: ['./edit-article-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditArticleCommentComponent implements OnInit {

    submitting = false;

articleComment: ArticleComment;

    constructor(private route: ActivatedRoute,private router:Router,private articleCommentService: ArticleCommentService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.articleCommentService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.articleComment = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }


  submitForm(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.articleComment.id;
            this.articleCommentService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/article-comment/list']);
                } else {
                    this.msgSrv.error('请求失败'+response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误'+error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
