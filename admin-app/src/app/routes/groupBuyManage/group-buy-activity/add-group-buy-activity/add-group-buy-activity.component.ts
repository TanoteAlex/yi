
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { GroupBuyActivityBo } from '../../../models/domain/bo/group-buy-activity-bo.model';
import { GroupBuyActivityService } from '../../../services/group-buy-activity.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-group-buy-activity',
    templateUrl: './add-group-buy-activity.component.html',
    styleUrls: ['./add-group-buy-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddGroupBuyActivityComponent implements OnInit {

    submitting = false;

    groupBuyActivity: GroupBuyActivityBo;

    constructor(private router:Router,private groupBuyActivityService: GroupBuyActivityService, public msgSrv: NzMessageService,
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
            this.groupBuyActivityService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/group-buy-activity/list']);
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
