
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { LogisticsAddress } from '../../../models/original/logistics-address.model';
import { LogisticsAddressService } from '../../../services/logistics-address.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-logistics-address',
    templateUrl: './add-logistics-address.component.html',
    styleUrls: ['./add-logistics-address.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddLogisticsAddressComponent implements OnInit {

    submitting = false;

    logisticsAddress: LogisticsAddress;

    constructor(private router:Router,private logisticsAddressService: LogisticsAddressService, public msgSrv: NzMessageService,
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
            this.logisticsAddressService.addForSupplier(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/logistics-address/list']);
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
