import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {DailyTaskService} from '../../../services/daily-task.service';
import {DailyTask} from '../../../models/original/daily-task.model';
import {IntegralTaskService} from "../../../services/integral-task.service";

@Component({
  selector: 'edit-daily-task',
  templateUrl: './edit-integral-task.component.html',
  styleUrls: ['./edit-integral-task.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditIntegralTaskComponent implements OnInit {

    submitting = false;

dailyTask: DailyTask;

    constructor(private route: ActivatedRoute,private router:Router,private integralTaskService: IntegralTaskService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.integralTaskService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dailyTask = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
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
            formValue.obj.id = this.dailyTask.id;
            this.integralTaskService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/dailyTask/list']);
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
