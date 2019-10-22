import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {SupplierCheckAccountService} from '../../../services/supplier-check-account.service';
import {SupplierCheckAccount} from '../../../models/original/supplier-check-account.model';

@Component({
  selector: 'edit-supplier-check-account',
  templateUrl: './edit-supplier-check-account.component.html',
  styleUrls: ['./edit-supplier-check-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditSupplierCheckAccountComponent implements OnInit {

    submitting = false;

supplierCheckAccount: SupplierCheckAccount;

    constructor(private route: ActivatedRoute,private router:Router,private supplierCheckAccountService: SupplierCheckAccountService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierCheckAccountService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierCheckAccount = response.data;
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
            formValue.obj.id = this.supplierCheckAccount.id;
            this.supplierCheckAccountService.update(formValue.obj).subscribe(response => {
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
