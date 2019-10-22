

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { QuestionTypeService } from "../../../services/question-type.service";
import { QuestionType } from "../../../models/original/question-type.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-question-type',
  templateUrl: './view-question-type.component.html',
  styleUrls: ['./view-question-type.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewQuestionTypeComponent implements OnInit {

questionType: QuestionType=new QuestionType;

    constructor(private route: ActivatedRoute,private location: Location,private questionTypeService: QuestionTypeService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.questionTypeService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.questionType = response.data;
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
