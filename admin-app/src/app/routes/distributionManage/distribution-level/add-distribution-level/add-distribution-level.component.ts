
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { DistributionLevelBo } from '../../../models/domain/bo/distribution-level-bo.model';
import { DistributionLevelService } from '../../../services/distribution-level.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-distribution-level',
    templateUrl: './add-distribution-level.component.html',
    styleUrls: ['./add-distribution-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddDistributionLevelComponent implements OnInit {

    submitting = false;

    distributionLevel: DistributionLevelBo;

    constructor(private router:Router,private distributionLevelService: DistributionLevelService, public msgSrv: NzMessageService,
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
            this.distributionLevelService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/distribution-level/list']);
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
