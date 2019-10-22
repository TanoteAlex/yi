import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";

import {Location} from "@angular/common";
import {ArticleService} from "../../../services/article.service";
import {Article} from "../../../models/original/article.model";
import {DomSanitizer} from "@angular/platform-browser";


@Component({
  selector: 'view-article',
  templateUrl: './view-article.component.html',
  styleUrls: ['./view-article.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewArticleComponent implements OnInit {

article: Article=new Article;

    constructor(public domSanitizer:DomSanitizer,private route: ActivatedRoute,private location: Location,private articleService: ArticleService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.articleService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.article = response.data;
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
