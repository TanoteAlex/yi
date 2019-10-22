
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SalesAreaBo } from '../../../models/domain/bo/sales-area-bo.model';
import { SalesAreaService } from '../../../services/sales-area.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-sales-area',
    templateUrl: './add-sales-area.component.html',
    styleUrls: ['./add-sales-area.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSalesAreaComponent implements OnInit {

    submitting = false;

    salesArea: SalesAreaBo;

    constructor(private router:Router,private salesAreaService: SalesAreaService, public msgSrv: NzMessageService,
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
            this.salesAreaService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/sales-area/list']);
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
