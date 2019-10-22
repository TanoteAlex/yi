

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { QuestionService } from "../../../services/question.service";
import { Question } from "../../../models/original/question.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-question',
  templateUrl: './view-question.component.html',
  styleUrls: ['./view-question.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewQuestionComponent implements OnInit {

question: Question=new Question;

    constructor(private route: ActivatedRoute,private location: Location,private questionService: QuestionService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.questionService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.question = response.data;
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
