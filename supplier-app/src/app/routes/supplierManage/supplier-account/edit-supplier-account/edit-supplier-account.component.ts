
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { SupplierAccountService } from '../../../services/supplier-account.service';
import { SupplierAccount } from '../../../models/original/supplier-account.model';

@Component({
  selector: 'edit-supplier-account',
  templateUrl: './edit-supplier-account.component.html',
  styleUrls: ['./edit-supplier-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditSupplierAccountComponent implements OnInit {

    submitting = false;

supplierAccount: SupplierAccount;

    constructor(private route: ActivatedRoute,private router:Router,private supplierAccountService: SupplierAccountService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierAccountService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierAccount = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
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
            formValue.obj.id = this.supplierAccount.id;
            this.supplierAccountService.update(formValue.obj).subscribe(response => {
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
