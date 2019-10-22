import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {DailyTask} from '../../../models/original/daily-task.model';
import {DailyTaskService} from '../../../services/daily-task.service';
import {SUCCESS} from '../../../models/constants.model';
import {IntegralTaskService} from "../../../services/integral-task.service";

@Component({
    selector: 'add-daily-task',
    templateUrl: './add-integral-task.component.html',
    styleUrls: ['./add-integral-task.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddIntegralTaskComponent implements OnInit {

    submitting = false;

    dailyTask: DailyTask;

    constructor(private router:Router,private integralTaskService: IntegralTaskService, public msgSrv: NzMessageService,
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
            this.integralTaskService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/daily-task/list']);
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
