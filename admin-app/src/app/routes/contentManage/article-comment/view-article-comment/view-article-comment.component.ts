

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { ArticleCommentService } from "../../../services/article-comment.service";
import { ArticleComment } from "../../../models/original/article-comment.model";
import { Location } from "@angular/common";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'view-article-comment',
  templateUrl: './view-article-comment.component.html',
  styleUrls: ['./view-article-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewArticleCommentComponent implements OnInit {

articleComment: ArticleComment=new ArticleComment;

    constructor(private route: ActivatedRoute,private location: Location,private articleCommentService: ArticleCommentService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,public sanli:DomSanitizer) { }

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
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
