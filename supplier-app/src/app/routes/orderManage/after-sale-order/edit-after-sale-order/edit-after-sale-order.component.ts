
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import {SUCCESS} from "../../../models/constants.model";
import { AfterSaleOrderService } from '../../../services/after-sale-order.service';
import { AfterSaleOrderBo } from '../../../models/domain/bo/after-sale-order-bo.model';

@Component({
  selector: 'edit-after-sale-order',
  templateUrl: './edit-after-sale-order.component.html',
  styleUrls: ['./edit-after-sale-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAfterSaleOrderComponent implements OnInit {

    submitting = false;

    afterSaleOrder: AfterSaleOrderBo;

    constructor(private route: ActivatedRoute,private router:Router,private afterSaleOrderService: AfterSaleOrderService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.afterSaleOrderService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.afterSaleOrder = response.data;
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
            formValue.obj.id = this.afterSaleOrder.id;
            this.afterSaleOrderService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/after-sale-order/list']);
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
