
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { Seckill } from '../../../models/original/seckill.model';
import { SeckillService } from '../../../services/seckill.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-seckill',
    templateUrl: './add-seckill.component.html',
    styleUrls: ['./add-seckill.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSeckillComponent implements OnInit {

    submitting = false;

    seckill: Seckill;

    constructor(private router:Router,private seckillService: SeckillService, public msgSrv: NzMessageService,
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
            this.seckillService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/seckill/list']);
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
