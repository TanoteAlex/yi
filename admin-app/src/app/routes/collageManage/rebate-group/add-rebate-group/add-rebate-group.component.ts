
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { RebateGroup } from '../../../models/original/rebate-group.model';
import { RebateGroupService } from '../../../services/rebate-group.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-rebate-group',
    templateUrl: './add-rebate-group.component.html',
    styleUrls: ['./add-rebate-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRebateGroupComponent implements OnInit {

    submitting = false;

    rebateGroup: RebateGroup;

    constructor(private router:Router,private rebateGroupService: RebateGroupService, public msgSrv: NzMessageService,
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
            this.rebateGroupService.add(formValue.obj).subscribe(response => {
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
