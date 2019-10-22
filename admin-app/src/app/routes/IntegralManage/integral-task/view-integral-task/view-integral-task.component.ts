import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {DailyTaskService} from "../../../services/daily-task.service";
import {DailyTask} from "../../../models/original/daily-task.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-daily-task',
  templateUrl: './view-integral-task.component.html',
  styleUrls: ['./view-integral-task.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewIntegralTaskComponent implements OnInit {

dailyTask: DailyTask=new DailyTask;

    constructor(private route: ActivatedRoute,private location: Location,private dailyTaskService: DailyTaskService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.dailyTaskService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dailyTask = response.data;
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
