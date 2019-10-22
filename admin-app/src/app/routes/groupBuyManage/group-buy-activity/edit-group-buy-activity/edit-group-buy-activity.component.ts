
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { GroupBuyActivityService } from '../../../services/group-buy-activity.service';
import { GroupBuyActivityBo } from '../../../models/domain/bo/group-buy-activity-bo.model';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'edit-group-buy-activity',
  templateUrl: './edit-group-buy-activity.component.html',
  styleUrls: ['./edit-group-buy-activity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditGroupBuyActivityComponent implements OnInit {

    submitting = false;

    groupBuyActivity: GroupBuyActivityBo;

    constructor(private route: ActivatedRoute,private router:Router,private groupBuyActivityService: GroupBuyActivityService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.groupBuyActivityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.groupBuyActivity = response.data;
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
            formValue.obj.id = this.groupBuyActivity.id;
            this.groupBuyActivityService.update(formValue.obj).subscribe(response => {
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
