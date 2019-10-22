
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { QuestionType } from '../../../models/original/question-type.model';
import { QuestionTypeService } from '../../../services/question-type.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-question-type',
    templateUrl: './add-question-type.component.html',
    styleUrls: ['./add-question-type.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddQuestionTypeComponent implements OnInit {

    submitting = false;

    questionType: QuestionType;

    constructor(private router:Router,private questionTypeService: QuestionTypeService, public msgSrv: NzMessageService,
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
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.questionTypeService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/question-type/list']);
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
