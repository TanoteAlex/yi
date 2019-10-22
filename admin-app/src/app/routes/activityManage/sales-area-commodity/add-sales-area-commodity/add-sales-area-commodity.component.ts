
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SalesAreaCommodityBo } from '../../../models/domain/bo/sales-area-commodity-bo.model';
import { SalesAreaCommodityService } from '../../../services/sales-area-commodity.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-sales-area-commodity',
    templateUrl: './add-sales-area-commodity.component.html',
    styleUrls: ['./add-sales-area-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSalesAreaCommodityComponent implements OnInit {

    submitting = false;

    salesAreaCommodity: SalesAreaCommodityBo;

    constructor(private router:Router,private salesAreaCommodityService: SalesAreaCommodityService, public msgSrv: NzMessageService,
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
            this.salesAreaCommodityService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/sales-area-commodity/list']);
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
