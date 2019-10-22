import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Article} from '../../../models/original/article.model';
import {ArticleService} from '../../../services/article.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-article',
    templateUrl: './add-article.component.html',
    styleUrls: ['./add-article.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddArticleComponent implements OnInit {

    submitting = false;

    article: Article;

    constructor(private router:Router,private articleService: ArticleService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue){
        if (formValue) {
          formValue.obj.articleType=1
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.articleService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/article/list']);
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
