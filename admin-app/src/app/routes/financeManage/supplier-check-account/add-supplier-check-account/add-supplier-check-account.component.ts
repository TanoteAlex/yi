import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SupplierCheckAccount} from '../../../models/original/supplier-check-account.model';
import {SupplierCheckAccountService} from '../../../services/supplier-check-account.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-supplier-check-account',
    templateUrl: './add-supplier-check-account.component.html',
    styleUrls: ['./add-supplier-check-account.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSupplierCheckAccountComponent implements OnInit {

    submitting = false;

    supplierCheckAccount: SupplierCheckAccount;

    constructor(private router:Router,private supplierCheckAccountService: SupplierCheckAccountService, public msgSrv: NzMessageService,
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
            this.supplierCheckAccountService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/supplier-check-account/list']);
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
