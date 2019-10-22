import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SUCCESS} from "../../models/constants.model";
import {CommentService} from "../../services/comment.service";
import {NzMessageService} from "ng-zorro-antd";
import {Router} from "@angular/router";
import {ArticleCommentService} from "../../services/article-comment.service";

@Component({
  selector: 'nz-reply-article-comment',
  template: `
    <a style="color: #0090ff" *ngIf="showButton" (click)="showModal()">{{buttonName}}
      <ng-content></ng-content>
    </a>
    <button nz-button nzType="primary" type="submit" *ngIf="!showButton" (click)="showModal()">{{buttonName}}
      
    </button>
    <nz-modal *ngIf="comment" [(nzVisible)]="isVisible" [nzTitle]="title" 
              (nzOnCancel)="handleCancel()" (nzOnOk)="handleOk()">
      <textarea nz-input style="width: 100%;height: 150px" [(ngModel)]="replys"></textarea>
    </nz-modal>
  `,
  styles: []
})
export class AlticleCommentComponent {
  isVisible = false;
  @Input()
  title = ''
  @Input()
  content = ''
  @Input()
  buttonName = 'Show Modal'
  @Input()
  showButton = true
  @Input()
  comment = false
  @Input()
  id=0;

  @Output()
  ok: EventEmitter<any> = new EventEmitter();

  replys="";

  constructor(public articleCommentServer:ArticleCommentService, public msg: NzMessageService){
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    if(this.comment){
      //回复
        if (this.replys=="") {
          this.msg.warning("回复内容不能为空")
          return
        }
        this.articleCommentServer.reply(this.id, this.replys).subscribe(response => {
          if (response.result == SUCCESS) {
            this.msg.success("回复成功");
          } else {
            this.msg.error('请求失败', response.message);
          }
        }, error => {
          this.msg.error('请求错误', error.message);
        });

    }
    this.ok.emit("ok");
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

}
