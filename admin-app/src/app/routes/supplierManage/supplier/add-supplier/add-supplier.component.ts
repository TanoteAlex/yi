import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Supplier} from '../../../models/original/supplier.model';
import {SupplierService} from '../../../services/supplier.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-supplier',
    templateUrl: './add-supplier.component.html',
    styleUrls: ['./add-supplier.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSupplierComponent implements OnInit {

    submitting = false;

    supplier: Supplier;

    constructor(private router: Router, private supplierService: SupplierService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue) {
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.supplierService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/supplier/list']);
                } else {
                    this.msgSrv.error('请求失败 ' + response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误 ' + error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }

}
