
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SupplierAccount } from '../../../models/original/supplier-account.model';
import { SupplierAccountService } from '../../../services/supplier-account.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-supplier-account',
    templateUrl: './add-supplier-account.component.html',
    styleUrls: ['./add-supplier-account.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSupplierAccountComponent implements OnInit {

    submitting = false;

    supplierAccount: SupplierAccount;

    constructor(private router:Router,private supplierAccountService: SupplierAccountService, public msgSrv: NzMessageService,
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
            this.supplierAccountService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/supplier-account/list']);
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
