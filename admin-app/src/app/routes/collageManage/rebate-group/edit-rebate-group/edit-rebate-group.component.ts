
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { RebateGroupService } from '../../../services/rebate-group.service';
import { RebateGroup } from '../../../models/original/rebate-group.model';

@Component({
  selector: 'edit-rebate-group',
  templateUrl: './edit-rebate-group.component.html',
  styleUrls: ['./edit-rebate-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditRebateGroupComponent implements OnInit {

    submitting = false;

rebateGroup: RebateGroup;

    constructor(private route: ActivatedRoute,private router:Router,private rebateGroupService: RebateGroupService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.rebateGroupService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.rebateGroup = response.data;
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
            formValue.obj.id = this.rebateGroup.id;
            this.rebateGroupService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/rebate-group/list']);
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
