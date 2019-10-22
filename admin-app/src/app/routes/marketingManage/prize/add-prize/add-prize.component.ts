
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { PrizeBo } from '../../../models/domain/bo/prize-bo.model';
import { PrizeService } from '../../../services/prize.service';
import {SUCCESS} from "../../../models/constants.model";

@Component({
    selector: 'add-prize',
    templateUrl: './add-prize.component.html',
    styleUrls: ['./add-prize.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddPrizeComponent implements OnInit {

    submitting = false;

    prize: PrizeBo;

    constructor(private router:Router,private prizeService: PrizeService, public msgSrv: NzMessageService,
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
            this.prizeService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/prize/list']);
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
