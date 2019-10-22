import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {SupplierSaleStatService} from '../../../services/supplier-sale-stat.service';
import {SupplierSaleStat} from '../../../models/original/supplier-sale-stat.model';

@Component({
    selector: 'edit-supplier-sale-stat',
    templateUrl: './edit-supplier-sale-stat.component.html',
    styleUrls: ['./edit-supplier-sale-stat.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditSupplierSaleStatComponent implements OnInit {

    submitting = false;

    supplierSaleStat: SupplierSaleStat;

    constructor(private route: ActivatedRoute, private router: Router, private supplierSaleStatService: SupplierSaleStatService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.supplierSaleStatService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierSaleStat = response.data;
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

    confirmSub(formValue) {
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.supplierSaleStat.supplierId;
            this.supplierSaleStatService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/platform-sale-stat/list']);
                } else {
                    this.msgSrv.error('请求失败' + response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误' + error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
