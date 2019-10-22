import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {CommentService} from "../../../services/comment.service";
import {Comment} from "../../../models/original/comment.model";
import {Location} from "@angular/common";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'view-comment',
  templateUrl: './view-comment.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ViewCommentComponent implements OnInit {

comment: Comment=new Comment;

    constructor(private route: ActivatedRoute,private location: Location,private commentService: CommentService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,public sanli:DomSanitizer) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.commentService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.comment = response.data;
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
