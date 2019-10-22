import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Comment} from '../../../models/original/comment.model';
import {CommentService} from '../../../services/comment.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
  selector: 'add-comment',
  templateUrl: './add-comment.component.html',
  encapsulation: ViewEncapsulation.None
})
export class AddCommentComponent implements OnInit {

  submitting = false;

  comment: Comment;

  constructor(private router: Router, private commentService: CommentService, public msgSrv: NzMessageService,
              private modalService: NzModalService) {
  }

  ngOnInit() {
  }

  onTransmitFormValueSubscription(event) {
    if (event) {
      this.confirmSub(event)
    }
  }

    confirmSub(formValue) {
      if (formValue) {
        this.submitting = true;
        const messageId = this.msgSrv.loading("正在提交...").messageId;
        this.commentService.add(formValue.obj).subscribe(response => {
          if (response.result == SUCCESS) {
            this.msgSrv.success("操作成功");
            this.router.navigate(['/dashboard/comment/list']);
          } else {
            this.msgSrv.error('请求失败' + response.message);
          }
          this.msgSrv.remove(messageId);
          this.submitting = false;
        }, error => {
          this.msgSrv.error('请求错误' + error.message);
          this.msgSrv.remove(messageId);
          this.submitting = false;
        });
      }
    }


}
