
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { JoinGroupBuyBo } from '../../../models/domain/bo/join-group-buy-bo.model';
import { JoinGroupBuyService } from '../../../services/join-group-buy.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-join-group-buy',
    templateUrl: './add-join-group-buy.component.html',
    styleUrls: ['./add-join-group-buy.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddJoinGroupBuyComponent implements OnInit {

    submitting = false;

    joinGroupBuy: JoinGroupBuyBo;

    constructor(private router:Router,private joinGroupBuyService: JoinGroupBuyService, public msgSrv: NzMessageService,
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
            this.joinGroupBuyService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/join-group-buy/list']);
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
