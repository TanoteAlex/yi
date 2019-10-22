
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { Question } from '../../../models/original/question.model';
import { QuestionService } from '../../../services/question.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-question',
    templateUrl: './add-question.component.html',
    styleUrls: ['./add-question.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddQuestionComponent implements OnInit {

    submitting = false;

    question: Question;

    constructor(private router:Router,private questionService: QuestionService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
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
            this.questionService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/question/list']);
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
